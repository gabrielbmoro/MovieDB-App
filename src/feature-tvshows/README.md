# feature-tvshows

## Purpose
Feature module — displays a paginated, filterable TV show grid. Users browse TV shows by category (`Popular`, `Top Rated`, `On The Air`, `Airing Today`) with infinite scroll.

## Primary Responsibility
- Render the TV show browsing screen (`TvShowsScreen`) with a `LazyVerticalStaggeredGrid` (2 columns)
- Provide category filter tabs (`FilterMenu`) and handle pagination via `PagingController`
- Coordinate data fetching through `TvShowsHandler` → `TvShowsRepository`

## Existing Functionalities
- **TvShowsScreen** — `Scaffold` with `AnimatedAppToolbar` (shows/hides on scroll), `FilterMenu` filter chips, `ShowsList` staggered grid, `NavigationBottomBar`, error handling via `ErrorScreen`
- **TvShowsViewModel** — extends `BaseViewModel<TvShowsState, TvShowsIntent, UiEvent>` + `PagingController` (`SimplePaging`)
  - Intents: `Setup`, `RequestMoreTvShows`, `SelectFilterMenuItem`
  - Pagination via `currentPage.collectLatest { ... }`
  - Error mapping: `HttpException` → `ErrorInfo.SOMETHING_WRONG_HAPPENED` or `NETWORK_ERROR`
  - Maintains `ImmutableList<TvShowCardInfo>` with deduplication on page append
- **TvShowsHandler** — bridges filter types to TMDB API category strings (`popular`, `top_rated`, `on_the_air`, `airing_today`) and delegates to `TvShowsRepository`
- **Model** — `TvShowsState` (tvShowCardInfos, menuItems, selectedFilterMenu, loading, error), `FilterMenuItem`, `TvShowFilterType` enum, `TvShowCardInfo` (tvShowId, tvShowTitle, tvShowPosterUrl)
- **DI** — `TvShowsModule` (`lazyModule`): `factory { TvShowsHandler(...) }`, `viewModel { TvShowsViewModel(...) }`
- **Tests** — `TvShowsHandlerTest`, `TvShowsViewModelTest` with hand-written `FakeTvShowsRepository` and `FakeLogger`

## Entry Points

| Entry Point | File | Signature | Role |
|---|---|---|---|
| Screen | `src/commonMain/kotlin/.../ui/screens/tvshows/TvShowsScreen.kt` | `@Composable fun TvShowsScreen()` | TV Shows screen — Scaffold with AnimatedAppToolbar + staggered grid + bottom bar |
| ViewModel | `src/commonMain/kotlin/.../ui/screens/tvshows/TvShowsViewModel.kt` | `class TvShowsViewModel : BaseViewModel<TvShowsState, TvShowsIntent, UiEvent>, PagingController by SimplePaging()` | MVI ViewModel — handles setup, pagination, filter changes |
| Handler | `src/commonMain/kotlin/.../components/TvShowsHandler.kt` | `class TvShowsHandler(repository: TvShowsRepository)` | Use-case bridge: TvShowFilterType enum → TMDB API category strings |
| Model | `src/commonMain/kotlin/.../ui/screens/tvshows/Model.kt` | `data class TvShowsState`, `sealed interface TvShowsIntent`, `data class TvShowCardInfo` | MVI state/intent definitions + UI model |
| DI | `src/commonMain/kotlin/.../di/TvShowsModule.kt` | `val featureTvShowsModule = lazyModule { }` | Koin lazy module — registers TvShowsHandler + TvShowsViewModel |

## Important Workflows

### Infinite Scroll Pagination
```
TvShowsScreen renders ShowsList → LazyVerticalStaggeredGrid
  → LaunchedEffect(canScrollForward): if (!canScrollForward) onRequestMore()
    → ViewModel fires TvShowsIntent.RequestMoreTvShows
      → PagingController.requestNextPage() → currentPage increments
      → collectLatest(currentPage) triggers:
        → TvShowsHandler.getTvShowsFromFilter(filter, page)
          → translates TvShowFilterType to API string ("popular", "top_rated", "on_the_air", "airing_today")
          → Calls TvShowsRepository.getTvShowsFromFilter(apiString, page)
        → Maps List<TvShow> → List<TvShowCardInfo>
        → addAllDistinctly() — deduplicates by tvShowId, appends to existing list
        → Updates TvShowsState.tvShowCardInfos
```

### Filter Change Flow
```
User taps FilterChip in FilterMenu → fires SelectFilterMenuItem intent
  → ViewModel updates state (selected filter + cleared list + loading)
  → Cancels previous pagination coroutine job
  → resetPaging() → currentPage resets to 1
  → updateAccordingToFilterType() → toggles selected state on FilterMenuItems
  → handleSetup() triggers fresh fetch for page 1 with new filter
```

### Error → Retry Flow
```
API call throws exception → BaseViewModel.launchIo routes to onFailure()
  → onFailure() maps throwable to ErrorInfo:
    - HttpException → ErrorInfo.SOMETHING_WRONG_HAPPENED
    - Other → ErrorInfo.NETWORK_ERROR
  → TvShowsScreen shows ErrorScreen(errorInfo, onRetry = { Setup intent })
  → User taps Retry → fires TvShowsIntent.Setup → full state reset + fresh fetch
```

## Critical Files

| File | Role |
|---|---|
| `.../ui/screens/tvshows/TvShowsScreen.kt` | Screen composable — Scaffold, toolbar animation, error/retry, scroll-to-top |
| `.../ui/screens/tvshows/TvShowsViewModel.kt` | ViewModel — pagination, filter switching, state deduplication, error mapping |
| `.../ui/screens/tvshows/Model.kt` | MVI state (`TvShowsState`), intents (`TvShowsIntent`), UI models (`FilterMenuItem`, `TvShowFilterType`, `TvShowCardInfo`) |
| `.../ui/screens/tvshows/TvShowsIntent.kt` | Intent sealed interface: `Setup`, `RequestMoreTvShows`, `SelectFilterMenuItem` |
| `.../components/TvShowsHandler.kt` | Use-case bridge — TvShowFilterType → TMDB API category string mapping |
| `.../ui/widgets/FilterMenu.kt` | `FilterMenu` — horizontal `LazyRow` of Material3 `FilterChip` items |
| `.../ui/widgets/ShowsList.kt` | `ShowsList` — `LazyVerticalStaggeredGrid` (2 cols) with `canScrollForward` pagination trigger |
| `.../di/TvShowsModule.kt` | Koin lazy module — DI wiring for handler + viewModel |

## Internal Dependencies

| Dependency | Relationship |
|---|---|
| `:domain` | `implementation` (TvShowsRepository, domain models) |
| `:designsystem` | `implementation` (MovieImage, AppToolbarTitle, NavigationBottomBar, ErrorScreen, AsyncImage) |
| `:platform` | `implementation` (BaseViewModel, PagingController, LoggerHelper, navigation) |

### Key External Libraries
- Compose Multiplatform, kotlinx-collections-immutable, lifecycle-viewmodel-compose, Koin, Navigation Compose

## External Dependencies (Consumers)
- `:composeApp` — aggregates `featureTvShowsModule` and renders `TvShowsScreen` at the `Screen.TvShows` route

## Technical Notes
- Uses `LazyVerticalStaggeredGrid` (not `LazyVerticalGrid`) for a masonry-like layout
- The `AnimatedAppToolbar` visibility is computed from `LazyStaggeredGridState.firstVisibleItemIndex`
- `TvShowCardInfo` is defined locally in `Model.kt` — a lightweight UI model separate from the domain `TvShow`
- Scroll-to-top helper `LazyStaggeredGridState.scrollToInit()` is an extension function defined in the screen file
- Filter selection triggers `resetPaging()` to restart from page 1
- FilterMenu applies extra start padding when the user is scrolled to the top of the filter row (`rememberIsAtStartState`)
- Fully multiplatform — no `androidMain` or `iosMain` source sets; everything lives in `commonMain`
- Compose string resources (`strings.xml`) contain localized labels for filter chips and the screen title

## Technical Debts
- `TvShowCardInfo` is duplicated across feature-tvshows, feature-movies, feature-search, and feature-wishlist — should be promoted to a shared module
- `FilterMenu` widget is duplicated between feature-tvshows and feature-movies — should be promoted to `:designsystem`
- `TvShowsHandler` is injected as a `@Factory` but could be `@Single` (stateless)
- No debounce on filter switching — rapid taps trigger multiple resets
- Error handling only distinguishes `HttpException` types; other exceptions fall through without user-visible messages
- **`!!` on nullable state** in `TvShowsScreen.kt`: `uiState.errorInfo!!` after null check — violates project convention
