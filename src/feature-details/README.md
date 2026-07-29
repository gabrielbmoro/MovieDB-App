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

## Entry Points

| Entry Point | File | Signature | Role |
|---|---|---|---|
| Screen | `src/commonMain/kotlin/.../ui/screens/details/DetailsScreen.kt` | `@Composable fun DetailsScreen(movieId: Long)` | Detail screen — loading/error/content states with scrollable layout |
| ViewModel | `src/commonMain/kotlin/.../ui/screens/details/DetailsViewModel.kt` | `class DetailsViewModel : BaseViewModel<DetailsUIState, DetailsUserIntent, UiEvent>` | MVI ViewModel — fetches detail + video, toggles favorite |
| Model | `src/commonMain/kotlin/.../ui/screens/details/Model.kt` | `data class DetailsUIState`, `sealed interface DetailsUserIntent` | MVI state (14 fields) + 3 intents |
| DI | `src/commonMain/kotlin/.../di/DetailsModule.kt` | `val featureDetailsModule = lazyModule { }` | Koin lazy module — registers DetailsViewModel |

## Important Workflows

### Details Load Flow
```
LaunchedEffect(movieId) fires LoadMovieDetails(movieId) intent
  → ViewModel.executeIntent → loadMovieDetails()
    → updateState { isLoading = true }
    → fetchMoviesDetails() — runCatching { getMovieDetailsUseCase.execute(Params(movieId)) }.getOrNull()
      → Use case fetches MovieDetail + VideoStreams from repository
      → Filters: first (site=="YouTube" && official && type=="Trailer") → videoId
      → Returns MovieDetail.copy(videoId = videoKey) or null on failure
    → If null: returns early (isLoading stays true — **permanent spinner bug**)
    → isMovieFavorite(movieDetail.title) — checks via repository
    → Updates state: movieDetail data, genres flattened, production companies joined, etc.
    → updateState { isLoading = false }
```

### Favorite Toggle Flow
```
User taps Favorite(heart) icon → fires FavoriteMovie intent
  → ViewModel.favoriteMovie() — NOT wrapped in runCatching
    → movieId = state.value.movieDetails?.let { movieId!! }  ← force unwrap
    → Checks current favorite status via repository.checkIsAFavoriteMovie(title)
    → Constructs FavoriteMovieUseCase.Params from MovieDetail
      → toFavorite = !currentFavoriteState (toggle)
    → Calls favoriteMovieUseCase.execute(params)
    → Re-checks favorite status after toggle
    → Updates state: isFavorite = new status
    ⚠ If repository or use case throws → coroutine silently killed (no runCatching)
    ⚠ If movieId is null → crash (!! usage)
```

### Error States
```
Loading state: isLoading == true → DetailsScreenLoading (BubbleLoader)
Error state: errorMessage != null → DetailsScreenError (ErrorMessage widget — static dino + "Sorry for that!")
  ⚠ The actual errorMessage string value is never displayed; ErrorMessage widget ignores it
Content state: !isLoading && errorMessage == null → DetailsScreenContent with all sections
  ⚠ errorMessage is never populated by ViewModel — always null in practice
```

## Critical Files

| File | Role |
|---|---|
| `.../ui/screens/details/DetailsScreen.kt` | Screen composable — 3-state rendering (loading/error/content) + toolbar animation |
| `.../ui/screens/details/DetailsViewModel.kt` | ViewModel — detail fetch, video enrichment, favorite toggle |
| `.../ui/screens/details/Model.kt` | MVI state (`DetailsUIState` 14 fields) + intents (`DetailsUserIntent`) |
| `.../ui/widgets/Favorite.kt` | Heart icon toggle (filled red / outlined), circle-shaped, clickable |
| `.../ui/widgets/MovieDetailIndicator.kt` | Row of Favorite button + FiveStars rating |
| `.../ui/widgets/GenresCard.kt` | FlowRow of secondary-colored genre chips |
| `.../ui/widgets/SectionTitle.kt` | `Text` wrapper with `titleMedium` style |
| `.../ui/widgets/SectionBody.kt` | `Text` wrapper with `bodyMedium` style |
| `.../ui/widgets/TextUrl.kt` | Clickable, italic, underlined URL that opens via `LocalUriHandler` |
| `.../ui/widgets/ErrorMessage.kt` | Static error state — dino illustration + "Sorry for that!" title |
| `.../di/DetailsModule.kt` | Koin lazy module — DI wiring for DetailsViewModel |

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
- **Permanent loading spinner bug**: `loadMovieDetails()` returns early when `fetchMoviesDetails()` returns null, but never sets `isLoading = false`. If the API call fails, the screen stays on the loading spinner forever.
- **`favoriteMovie()` has no `runCatching` wrapper** — calls `favoriteMovieUseCase.execute()` unprotected. Any exception kills the coroutine with no user feedback.
- **`SideEffect { println(...) }` debug artifact** in `DetailsScreen.kt` — prints UI state to stdout on every recomposition. Should use `LoggerHelper` or be removed.
- **`Color.Red` hardcoded** in `Favorite.kt` widget — not theme-aware; won't adapt to dark/light mode correctly.
- **`ErrorMessage` widget ignores the actual error message** — displays a static illustration and title regardless of what `errorMessage: String?` contains.
