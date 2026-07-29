# feature-movies

## Purpose
Feature module — displays a paginated, filterable movie grid as the app's home screen. Users browse movies by category (`Popular`, `Top Rated`, `Upcoming`, `Now Playing`) with infinite scroll.

## Primary Responsibility
- Render the movie browsing screen (`MoviesScreen`) with a `LazyVerticalStaggeredGrid` (2 columns)
- Provide category filter tabs (`FilterMenu`) and handle pagination via `PagingController`
- Coordinate data fetching through `MoviesHandler` → `MoviesRepository`

## Existing Functionalities
- **MoviesScreen** — `Scaffold` with `AnimatedAppToolbar` (shows/hides on scroll), `FilterMenu` filter chips, `MoviesList` staggered grid, `NavigationBottomBar`, error handling via `ErrorScreen`
- **MoviesViewModel** — extends `BaseViewModel<MoviesState, MoviesIntent, UiEvent>` + `PagingController` (`SimplePaging`)
  - Intents: `Setup`, `RequestMoreMovies`, `SelectFilterMenuItem`
  - Pagination via `currentPage.collectLatest { ... }`
  - Error mapping: `HttpException` → `ErrorInfo.SOMETHING_WRONG_HAPPENED` or `NETWORK_ERROR`
  - Maintains `ImmutableList<MovieCardInfo>` with deduplication on page append
- **MoviesHandler** — bridges filter types to TMDB API category strings (`popular`, `top_rated`, `upcoming`, `now_playing`) and delegates to `MoviesRepository`
- **Model** — `MoviesState` (movies, filters, loading, error), `FilterMenuItem`, `FilterType` enum, `MovieCardInfo` (movieId, title, posterUrl)
- **DI** — `MoviesModule` (`lazyModule`): `factory { MoviesHandler(...) }`, `viewModel { MoviesViewModel(...) }`
- **Tests** — `MoviesHandlerTest`, `MoviesViewModelTest` with hand-written `FakeRepository` and `FakeLogger`

## Entry Points

| Entry Point | File | Signature | Role |
|---|---|---|---|
| Screen | `src/commonMain/kotlin/.../ui/screens/movies/MoviesScreen.kt` | `@Composable fun MoviesScreen()` | Home screen — Scaffold with AnimatedAppToolbar + staggered grid + bottom bar |
| ViewModel | `src/commonMain/kotlin/.../ui/screens/movies/MoviesViewModel.kt` | `class MoviesViewModel : BaseViewModel<MoviesState, MoviesIntent, UiEvent>, PagingController by SimplePaging()` | MVI ViewModel — handles setup, pagination, filter changes |
| Handler | `src/commonMain/kotlin/.../components/MoviesHandler.kt` | `class MoviesHandler(repository: MoviesRepository)` | Use-case bridge: FilterType enum → TMDB API category strings |
| Model | `src/commonMain/kotlin/.../ui/screens/movies/Model.kt` | `data class MoviesState`, `sealed interface MoviesIntent`, `data class MovieCardInfo` | MVI state/intent definitions + UI model |
| DI | `src/commonMain/kotlin/.../di/MoviesModule.kt` | `val featureMoviesModule = lazyModule { }` | Koin lazy module — registers MoviesHandler + MoviesViewModel |

## Important Workflows

### Infinite Scroll Pagination
```
MoviesScreen renders MoviesList → LazyVerticalStaggeredGrid
  → LaunchedEffect(canScrollForward): if (!canScrollForward) onRequestMore()
    → ViewModel fires MoviesIntent.RequestMoreMovies
      → PagingController.requestNextPage() → currentPage increments
      → collectLatest(currentPage) triggers:
        → MoviesHandler.getMoviesFromFilter(filter, page)
          → translates FilterType to API string ("popular", "top_rated", "upcoming", "now_playing")
          → Calls MoviesRepository.getMoviesFromFilter(apiString, page)
        → Maps List<Movie> → List<MovieCardInfo>
        → addAllDistinctly() — deduplicates by movieId, appends to existing list
        → Updates MoviesState.movieCardInfos
```

### Filter Change Flow
```
User taps FilterChip in FilterMenu → fires SelectFilterMenuItem intent
  → ViewModel cancels previous pagination coroutine job
  → resetPaging() → currentPage resets to 1
  → updateAccordingToFilterType() → toggles selected state on FilterMenuItems
  → Updates state with selected filter + cleared movie list
  → collectLatest(currentPage) triggers fresh fetch for page 1 with new filter
```

### Error → Retry Flow
```
API call throws exception → BaseViewModel.launchIo routes to onFailure()
  → onFailure() maps throwable to ErrorInfo:
    - HttpException → ErrorInfo.SOMETHING_WRONG_HAPPENED
    - Other → ErrorInfo.NETWORK_ERROR
  → MoviesScreen shows ErrorScreen(errorInfo, onRetry = { Setup intent })
  → User taps Retry → fires MoviesIntent.Setup → full state reset + fresh fetch
```

## Critical Files

| File | Role |
|---|---|
| `.../ui/screens/movies/MoviesScreen.kt` | Screen composable — Scaffold, toolbar animation, error/retry, scroll-to-top |
| `.../ui/screens/movies/MoviesViewModel.kt` | ViewModel — pagination, filter switching, state deduplication, error mapping |
| `.../ui/screens/movies/Model.kt` | MVI state (`MoviesState`), intents (`MoviesIntent`), UI models (`FilterMenuItem`, `FilterType`, `MovieCardInfo`) |
| `.../ui/screens/movies/MoviesIntent.kt` | Intent sealed interface: `Setup`, `RequestMoreMovies`, `SelectFilterMenuItem` |
| `.../components/MoviesHandler.kt` | Use-case bridge — FilterType → TMDB API category string mapping |
| `.../ui/widgets/FilterMenu.kt` | `FilterMenu` — horizontal `LazyRow` of Material3 `FilterChip` items |
| `.../ui/widgets/MoviesList.kt` | `MoviesList` — `LazyVerticalStaggeredGrid` (2 cols) with `canScrollForward` pagination trigger |
| `.../di/MoviesModule.kt` | Koin lazy module — DI wiring for handler + viewModel |

## Internal Dependencies

| Dependency | Relationship |
|---|---|
| `:domain` | `implementation` (MoviesRepository, domain models) |
| `:designsystem` | `implementation` (MovieCard, AppToolbarTitle, NavigationBottomBar, FiveStars, ErrorScreen, BubbleLoader, AsyncImage) |
| `:platform` | `implementation` (BaseViewModel, PagingController, LoggerHelper, navigation) |

### Key External Libraries
- Compose Multiplatform, kotlinx-collections-immutable, lifecycle-viewmodel-compose, Koin, Navigation Compose

## External Dependencies (Consumers)
- `:composeApp` — aggregates `featureMoviesModule` and renders `MoviesScreen` at the `Screen.Movies` route

## Technical Notes
- Uses `LazyVerticalStaggeredGrid` (not `LazyVerticalGrid`) for a masonry-like layout
- The `AnimatedAppToolbar` visibility is computed from `LazyStaggeredGridState.firstVisibleItemIndex`
- `MovieCardInfo` is defined locally in `Model.kt` — a lightweight UI model separate from the domain `Movie`
- Scroll-to-top helper `LazyStaggeredGridState.scrollToInit()` is an extension function defined in the screen file
- Filter selection triggers `resetPaging()` to restart from page 1

## Technical Debts
- `MovieCardInfo` is duplicated across feature-movies, feature-search, and feature-wishlist — should be promoted to a shared module
- `MoviesHandler` is injected as a `@Factory` but could be `@Single` (stateless)
- No debounce on filter switching — rapid taps trigger multiple resets
- Error handling only distinguishes `HttpException` types; other exceptions fall through without user-visible messages
- **`MoviesHandler.kt` has no package declaration** — lives in root/default package despite being in directory `feature/movies/components/`. Imported as `import MoviesHandler` throughout the module.
- **`!!` on nullable state** in `MoviesScreen.kt` line 99: `uiState.errorInfo!!` after null check — violates project convention.
- **Inconsistent test imports**: test files import `LoggerHelper` as `com.gabrielbmoro.moviedb.logging.LoggerHelper` instead of the correct `com.gabrielbmoro.moviedb.platform.logging.LoggerHelper`.
