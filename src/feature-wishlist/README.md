# feature-wishlist

## Purpose
Feature module — displays the user's favorite (wishlist) movies with swipe-to-delete and a confirmation dialog. Accessed via the bottom navigation bar's favorite tab.

## Primary Responsibility
- Render the wishlist screen (`WishlistScreen`) with a `LazyColumn` of `MovieCard` items
- Load all favorite movies from `MoviesRepository.getFavoriteMovies()`
- Support deletion of individual favorites via `FavoriteMovieUseCase` with a confirmation `AlertDialog`

## Existing Functionalities
- **WishlistScreen** — `Scaffold` with `AppToolbarTitle`, `NavigationBottomBar`, and `SnackbarHost`
  - Three content states: loading (`BubbleLoader`), empty (`EmptyState`), and populated (`MovieList`)
  - Collects `uiEvent` via `LaunchedEffect` — shows a snackbar on successful deletion
  - `LaunchedEffect(Unit)` triggers `LoadMovies` intent on first composition
  - Bottom bar double-tap on the active favorites tab scrolls the list to top
- **WishlistViewModel** — extends `BaseViewModel<WishlistUIState, WishlistUserIntent, WishlistUiEvent>`
  - Intents: `LoadMovies`, `PrepareToDeleteMovie(movie)`, `DeleteMovie`, `HideConfirmDeleteDialog`
  - `handleLoadMovies()` — fetches favorites from repository, maps `Movie` → `MovieCardInfo`, updates state
  - `handlePrepareToDeleteMovie()` — stores the target movie in `_movieToBeDeleted` and shows the dialog
  - `handleDeleteMovie()` — calls `FavoriteMovieUseCase.execute(toFavorite = false)`, reloads list, fires `ShowSuccessfulDeleteMessage` event, hides dialog
  - `handleHideConfirmDeleteDialog()` — sets `isDeleteAlertDialogVisible = false`
  - `onCleared()` nullifies `_movieToBeDeleted`
- **Model** — `WishlistUIState` (favoriteMovies, isLoading, areBarsVisible, isDeleteAlertDialogVisible), `WishlistUserIntent` (4 intents), `WishlistUiEvent` (ShowSuccessfulDeleteMessage)
- **Widgets**
  - `MovieList` — `LazyColumn` rendering `MovieCard` items with `enableDelete = true`; defines `MovieCardInfo` data class (`@Stable @Immutable`, fields: id, title, votesAverage, overview, posterImageUrl); items keyed by `id`
  - `DeleteConfirmationDialog` — Material3 `AlertDialog` with localized title/body/confirm/dismiss text; visible controlled by `visible: Boolean` parameter
- **DI** — `featureWishlistModule` (`lazyModule`): `viewModel { WishlistViewModel(repository = get(), favoriteMovieUseCase = get(), ioCoroutinesDispatcher = Dispatchers.IO) }`
- **Tests** — `WishlistViewModelTest` with hand-written `FakeRepository` and `FakeFavoriteMovieUseCase`; tests cover prepare-to-delete, load movies, and end-to-end delete flow

## Internal Dependencies

| Dependency | Relationship |
|---|---|
| `:domain` | `implementation` (MoviesRepository, FavoriteMovieUseCase, domain models) |
| `:designsystem` | `implementation` (MovieCard, AppToolbarTitle, NavigationBottomBar, BubbleLoader, EmptyState) |
| `:platform` | `implementation` (BaseViewModel, LoggerHelper, navigation) |

### Key External Libraries
- Compose Multiplatform, kotlinx-collections-immutable, lifecycle-viewmodel-compose, Koin, Navigation Compose

## External Dependencies (Consumers)
- `:composeApp` — aggregates `featureWishlistModule` and renders `WishlistScreen` at the `Screen.Wishlist` route

## Technical Notes
- The delete flow involves two intents: `PrepareToDeleteMovie` (shows dialog) → `DeleteMovie` (executes deletion). This ensures the user confirms before removing a favorite.
- `_movieToBeDeleted` is stored as a ViewModel field (outside MVI state) to bridge between prepare and delete intents.
- `toMovieCardInfo()` maps `Movie` → `MovieCardInfo` locally in the ViewModel.
- Bottom bar fast-tap on the current tab scrolls the `LazyColumn` to item 0 via `lazyListState.scrollToItem(0)`.

## Technical Debts
- **`onFailure` is a no-op**: errors during load or delete are silently ignored — no error state, no user feedback. `WishlistUIState` has no `errorMessage` field.
- **`!!` on nullable state**: `WishlistScreen` uses `uiState.value.favoriteMovies!!` which crashes if the list is null at composition time.
- **Duplicate `MovieCardInfo`**: defined locally in `MovieList.kt` but also exists in `feature-movies/Model.kt` and `feature-search/MoviesResult.kt`. Promote to a shared module.
- `_movieToBeDeleted` is stored outside MVI state as a regular `var` — a source-of-truth split that could lead to inconsistency if state restoration is needed.
