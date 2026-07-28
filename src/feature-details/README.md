# feature-details

## Purpose
Feature module — displays full movie details including backdrop, rating, favorite toggle, genres, production info, and video trailers. Accessed via deep link or card tap with a `movieId` argument.

## Primary Responsibility
- Render the movie detail screen (`DetailsScreen`) with a vertically scrollable layout
- Fetch movie details + video streams via `GetMovieDetailsUseCase` + `MoviesRepository`
- Persist/remove favorites via `FavoriteMovieUseCase`

## Existing Functionalities
- **DetailsScreen** — three rendering states: loading (`BubbleLoader`), error (`ErrorMessage`), content (scrollable `Column`)
  - Content sections: `VideoPlayer` (backdrop, or YouTube iframe if video exists), `MovieDetailIndicator` (star rating + `Favorite` button), `GenresCard` (flow row of chips), `SectionTitle`/`SectionBody` pairs for overview, popularity, language, tagline, production companies, `TextUrl` for homepage
  - `LaunchedEffect(movieId)` triggers `LoadMovieDetails` intent
- **DetailsViewModel** — extends `BaseViewModel<DetailsUIState, DetailsUserIntent, UiEvent>`
  - Intents: `LoadMovieDetails(movieId)`, `FavoriteMovie`, `HideVideo`
  - `fetchMoviesDetails()` uses `runCatching { ... }.getOrNull()` for concurrent detail + favorite check
  - `favoriteMovie()` toggles via use case (favorite → unfavorite, unfavorite → favorite)
- **Model** — `DetailsUIState` (14 fields: loading, favorite, videoId, genres, errorMessage, etc.), `DetailsUserIntent` (HideVideo, FavoriteMovie, LoadMovieDetails)
- **Widgets** — `Favorite` (heart icon toggle), `MovieDetailIndicator`, `GenresCard`, `SectionTitle`, `SectionBody`, `TextUrl` (clickable link), `ErrorMessage` (dino error image)
- **DI** — `DetailsModule` (`lazyModule`): `viewModel { DetailsViewModel(...) }` (no separate handler/factory)
- **Tests** — `DetailsViewModelTest` with hand-written `Fakes`

## Internal Dependencies

| Dependency | Relationship |
|---|---|
| `:domain` | `implementation` (MoviesRepository, FavoriteMovieUseCase, GetMovieDetailsUseCase, domain models) |
| `:designsystem` | `implementation` (AsyncImage, FiveStars, BubbleLoader, BackNavigationIcon, AppToolbarTitle) |
| `:platform` | `implementation` (BaseViewModel, VideoPlayer, LoggerHelper, navigation) |

### Key External Libraries
- Compose Multiplatform, kotlinx-collections-immutable, lifecycle-viewmodel-compose, Koin, Navigation Compose

## External Dependencies (Consumers)
- `:composeApp` — aggregates `featureDetailsModule` and renders `DetailsScreen` at the `Screen.Details/{movieId}` route

## Technical Notes
- `VideoPlayer` uses the `expect`/`actual` pattern from `platform` — embeds YouTube iframes in `WebView` (Android) or `WKWebView` (iOS)
- Genre names are flattened from `MovieDetail.genres: List<String>`
- `AnimatedAppToolbar` title appears only when user scrolls to top (animated visibility)
- Back navigation is provided by `BackNavigationIcon` from designsystem

## Technical Debts
- **Silent error swallowing**: `fetchMoviesDetails()` uses `runCatching { ... }.getOrNull()` — errors are never logged and `onFailure` is a no-op. The `errorMessage` field in `DetailsUIState` is permanently `null`.
- **Force unwraps (`!!`)**: `movieId!!` and `movieDetails!!` in the ViewModel crash the app if null (e.g., if state hasn't initialized before subsequent intents)
- `DetailsUIState` has 14 fields with a manual `empty()` factory — consider sealed interface states (Loading/Error/Content) instead
- No error handling for `FavoriteMovie` intent — if the toggle fails, UI silently reverts
