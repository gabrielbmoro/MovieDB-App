---
module: feature-wishlist
summary: "Favorited movies list with two-step swipe-to-delete and confirmation dialog."
keywords: [wishlist, favorites, delete, confirmation-dialog, snackbar, movie-card, lazy-column]
---

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

## Entry Points

| Entry Point | File | Signature | Role |
|---|---|---|---|
| Screen | `src/commonMain/kotlin/.../ui/screens/wishlist/WishlistScreen.kt` | `@Composable fun WishlistScreen()` | Wishlist screen — Scaffold with toolbar + LazyColumn + bottom bar + Snackbar |
| ViewModel | `src/commonMain/kotlin/.../ui/screens/wishlist/WishlistViewModel.kt` | `class WishlistViewModel : BaseViewModel<WishlistUIState, WishlistUserIntent, WishlistUiEvent>` | MVI ViewModel — load favorites, two-step delete, event emission |
| Model | `src/commonMain/kotlin/.../ui/screens/wishlist/Model.kt` | `data class WishlistUIState`, `sealed interface WishlistUserIntent`, `sealed class WishlistUiEvent` | MVI state + 4 intents + 1 one-shot event |
| DI | `src/commonMain/kotlin/.../di/WishlistModule.kt` | `val featureWishlistModule = lazyModule { }` | Koin lazy module — registers WishlistViewModel |

## Important Workflows

### Favorites Load Flow
```
WishlistScreen composes → LaunchedEffect(Unit) fires LoadMovies intent
  → ViewModel.handleLoadMovies()
    → repository.getFavoriteMovies()  ⚠ NOT wrapped in runCatching
    → Maps List<Movie> → List<MovieCardInfo> via toMovieCardInfo()
    → Updates state: favoriteMovies = mapped list, isLoading = false
  → Screen renders one of three states:
    - isLoading: BubbleLoader centered
    - favoriteMovies == null: EmptyState (sad emoji + "No movie here")
    - favoriteMovies non-null non-empty: MovieList (LazyColumn of MovieCards)
```

### Two-Step Delete Flow
```
Step 1: User taps delete icon on a MovieCard
  → fires PrepareToDeleteMovie(movie) intent
  → ViewModel.handlePrepareToDeleteMovie()
    → Stores movie in _movieToBeDeleted field (outside MVI state)
    → Sets state: isDeleteAlertDialogVisible = true
  → Screen shows DeleteConfirmationDialog AlertDialog
    → Title: "Delete Movie?", Body: deletion confirmation message
    → Positive button: "Delete", Negative button: "Cancel"

Step 2a: User confirms deletion (taps "Delete")
  → fires DeleteMovie intent
  → ViewModel.handleDeleteMovie()
    → favoriteMovieUseCase.execute(Params(toFavorite = false, ...))  ⚠ NOT wrapped in runCatching
    → Reloads favorite movies (handleLoadMovies)
    → Fires one-shot event: ShowSuccessfulDeleteMessage
    → Clears _movieToBeDeleted
    → Hides dialog: isDeleteAlertDialogVisible = false

Step 2b: User cancels (taps "Cancel")
  → fires HideConfirmDeleteDialog intent
  → ViewModel.hideConfirmDeleteDialog(): sets isDeleteAlertDialogVisible = false
```

### Snackbar Event Flow
```
DeleteMovie intent → ViewModel.handleDeleteMovie()
  → fireEvent(WishlistUiEvent.ShowSuccessfulDeleteMessage)
  → Screen's LaunchedEffect collects uiEvent flow:
    LaunchedEffect(Unit) {
      viewModel.uiEvent.collect { event ->
        when (event) {
          is WishlistUiEvent.ShowSuccessfulDeleteMessage ->
            snackbarHostState.showSnackbar("Movie deleted successfully")
        }
      }
    }
```

### Bottom Bar Double-Tap Scroll-to-Top
```
User taps Favorite tab on NavigationBottomBar while already on Wishlist screen
  → navigator.navigateToMovies() is NOT called (screen detects it's already on Wishlist)
  → Detects: currentTab != MoviesTabIndex
  → Calls lazyListState.scrollToItem(0) — scrolls list to top
```

## Critical Files

| File | Role |
|---|---|
| `.../ui/screens/wishlist/WishlistScreen.kt` | Screen composable — 3-state rendering, Snackbar, bottom bar fast-tap |
| `.../ui/screens/wishlist/WishlistViewModel.kt` | ViewModel — favorites load, two-step delete, one-shot events |
| `.../ui/screens/wishlist/Model.kt` | MVI state (`WishlistUIState`), intents (`WishlistUserIntent`), events (`WishlistUiEvent`) |
| `.../ui/widgets/MovieList.kt` | LazyColumn of MovieCards with delete enabled + `MovieCardInfo` data class |
| `.../ui/widgets/DeleteConfirmationDialog.kt` | Material3 AlertDialog — title, body, confirm/cancel actions |
| `.../di/WishlistModule.kt` | Koin lazy module — DI wiring for WishlistViewModel |

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
- **Missing `runCatching` on repository/useCase calls**: `handleLoadMovies()` calls `repository.getFavoriteMovies()` and `handleDeleteMovie()` calls `favoriteMovieUseCase.execute()` — neither is wrapped in `runCatching`. Any exception kills the coroutine.
- **Missing `LoggerHelper` injection**: The ViewModel does not inject `LoggerHelper`, making it impossible to log errors even if `onFailure` were implemented.
- **`areBarsVisible` dead field**: declared in `WishlistUIState` but never read by any composable in the module.
- **`delete_fail_message` dead resource**: defined in `strings.xml` as "Something went wrong" but never referenced in any source file.
