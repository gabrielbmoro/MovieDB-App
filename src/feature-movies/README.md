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
