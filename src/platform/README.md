# platform

## Purpose
Platform abstraction module — provides navigation infrastructure, MVI base classes, pagination controller, logging abstraction, and platform-specific video player. A LEAF module with no project dependencies.

## Primary Responsibility
- Define the navigation route enum (`Screen`) and Compose navigation helper extensions
- Provide `BaseViewModel` as the MVI foundation for all feature ViewModels
- Offer `SimplePaging` as a reusable pagination primitive
- Abstract platform-specific concerns: logging (`LoggerHelper`) and video playback (`VideoPlayer`)

## Existing Functionalities
- **Navigation**
  - `Screen` enum — `Movies`, `Details/{movieId}`, `Search?query=`, `Wishlist` with route constants
  - `NavHostGraphBuilderExt.kt` — DSL extensions: `addMoviesScreen`, `addMovieDetailsScreen`, `addWishlistScreen`, `addSearchScreen` (with argument extraction)
  - `NavHostControllerExt.kt` — navigation helper functions: `navigateToDetails(movieId)`, `navigateToMovies()`, `navigateToSearch(query)`, `navigateToWishlist()`
  - `LocalNavController.kt` — `CompositionLocal<NavHostController>` for accessing the nav controller from any composable
- **MVI Foundation**
  - `BaseViewModel<State, Intent, Event>` — abstract class providing `StateFlow<State>`, `SharedFlow<Event>`, `executeIntent()`, `updateState()`, `fireEvent()`, `launchIo()` (with `runCatching` safety), `onFailure()` callback
  - `Models.kt` — marker interfaces: `UiState`, `UserIntent`, `UiEvent`
- **Pagination** — `PagingController` interface + `SimplePaging` implementation (starts at page 1, increments on `requestNextPage()`, resets with `resetPaging()`)
- **Logging** — `LoggerHelper` interface + `LoggerHelperImpl` (Kermit wrapper with `logDebug`, `logInfo`, `logError`)
- **Video Player** — `expect fun VideoPlayer(videoId, modifier)` composable, actual implementations using `WebView` (Android) and `WKWebView` (iOS) with YouTube iframe embeds
- **Tests** — `SimplePagingTest` verifies reset and increment behavior

## Entry Points

| Entry Point | File | Signature | Role |
|---|---|---|---|
| MVI base | `src/commonMain/kotlin/.../viewmodel/BaseViewModel.kt` | `abstract class BaseViewModel<State: UiState, Intent: UserIntent, Event: UiEvent>` | MVI foundation — all feature ViewModels extend this |
| MVI markers | `src/commonMain/kotlin/.../viewmodel/Models.kt` | `interface UiState`, `interface UiEvent`, `interface UserIntent` | Marker interfaces enforced by MVI type params |
| Navigation routes | `src/commonMain/kotlin/.../navigation/Screen.kt` | `enum class Screen(route, firstSegment)` | Single source of truth for all navigation routes and deep link segments |
| Nav extensions | `src/commonMain/kotlin/.../navigation/NavHostControllerExt.kt` | Extension functions on `NavHostController` | `navigateToDetails()`, `navigateToSearch()`, `navigateToWishlist()`, `navigateToMovies()` |
| Graph builder | `src/commonMain/kotlin/.../navigation/NavHostGraphBuilderExt.kt` | Extension functions on `NavGraphBuilder` | `addMoviesScreen()`, `addMovieDetailsScreen()`, `addSearchScreen()`, `addWishlistScreen()` |
| Nav controller | `src/commonMain/kotlin/.../LocalNavController.kt` | `val LocalNavController = compositionLocalOf<NavHostController>` | CompositionLocal for accessing nav controller from any composable |
| Pagination | `src/commonMain/kotlin/.../paging/PagingController.kt` | `interface PagingController` + `class SimplePaging : PagingController` | Page counter with `requestNextPage()` / `resetPaging()` |
| Logging | `src/commonMain/kotlin/.../logging/Logger.kt` | `interface LoggerHelper` + `internal class LoggerHelperImpl` | Kermit wrapper — `logDebug`, `logInfo`, `logError` |
| Video player | `src/commonMain/kotlin/.../media/VideoPlayer.kt` | `expect fun VideoPlayer(videoId: String, modifier: Modifier)` | Cross-platform YouTube embed via WebView (Android) / WKWebView (iOS) |

## Important Workflows

### MVI Lifecycle (BaseViewModel)
```
Feature ViewModel extends BaseViewModel<State, Intent, Event>
  → Constructor: requires CoroutineDispatcher for IO, calls super(ioDispatcher)
    → uiState: StateFlow<State> = MutableStateFlow(defaultEmptyState()).stateIn(SharingStarted.Eagerly)
    → uiEvent: SharedFlow<Event> = MutableSharedFlow()
  
Feature Screen calls viewModel.executeIntent(intent)
  → ViewModel.executeIntent(intent) routes to private handler methods
    → launchIo { ... }  ← wraps in runCatching via BaseViewModel
      → runCatching { block() }
      → Success: result returned normally
      → CancellationException: rethrown (cooperative cancellation)
      → Other throwable: routed to onFailure(throwable)

Feature ViewModel updates state:
  → updateState { currentState -> currentState.copy(field = newValue) }
  → MutableStateFlow.update { } — atomic, thread-safe

Feature ViewModel emits one-shot events:
  → fireEvent(event)
  → MutableSharedFlow.emit(event)
  → Screen collects via LaunchedEffect { viewModel.uiEvent.collect { ... } }
```

### Pagination Flow
```
ViewModel delegates to PagingController by SimplePaging()
  → currentPage: StateFlow<Int> starts at 1
  → ViewModel.collectLatest(currentPage) { page -> fetchMovies(page); appendToState() }
  → Screen detects scroll-to-end → fires RequestMoreMovies intent
    → ViewModel calls pagingController.requestNextPage()
      → currentPage increments to 2 → collectLatest triggers fetch for page 2
  → Filter change fires SelectFilterMenuItem intent
    → ViewModel calls pagingController.resetPaging()
      → currentPage resets to 1 → collectLatest triggers fresh fetch for page 1
```

### Navigation Deep Link Routing
```
Screen enum maps:
  - Screen.Movies → route="movies", firstSegment=null (start destination, not deep-linked)
  - Screen.Search → route="Search?query={query}", firstSegment="search"
  - Screen.Details → route="details?movieId={movieId}", firstSegment="movie"
  - Screen.Wishlist → route="wishlist", firstSegment="favorite"

Rinku deep link: movie://movie/123
  → DeeplinkEffect (in :composeApp) matches "movie" → Screen.Details.firstSegment
  → Extracts 123 as movieId from path
  → Calls navController.navigateToDetails(123L)
    → String.detailsRoute(123) → "details?movieId=123"
    → navController.navigate("details?movieId=123")

Rinku deep link: movie://favorite
  → DeeplinkEffect matches "favorite" → Screen.Wishlist.firstSegment
  → Calls navController.navigateToWishlist()
    → navController.navigate("wishlist")
```

## Critical Files

| File | Role |
|---|---|
| `.../viewmodel/BaseViewModel.kt` | MVI base class — StateFlow, SharedFlow, Intent dispatch, error handling |
| `.../viewmodel/Models.kt` | MVI marker interfaces: `UiState`, `UserIntent`, `UiEvent` |
| `.../navigation/Screen.kt` | Route enum — single source of truth for navigation + deep link paths |
| `.../navigation/NavHostGraphBuilderExt.kt` | DSL extensions for building NavHost routes (composable registration) |
| `.../navigation/NavHostControllerExt.kt` | Navigation action helpers (navigateTo*) + route string builders |
| `.../LocalNavController.kt` | CompositionLocal — exposes NavHostController to composable tree |
| `.../paging/PagingController.kt` | PagingController interface + SimplePaging implementation |
| `.../logging/Logger.kt` | LoggerHelper interface + LoggerHelperImpl (Kermit wrapper) |
| `.../media/VideoPlayer.kt` (common) | `expect` composable — YouTube iframe HTML generator |
| `.../media/VideoPlayer.android.kt` | Android actual — WebView interop via `AndroidView` |
| `.../media/VideoPlayer.ios.kt` | iOS actual — WKWebView interop via `UIKitView` |
| `.../di/PlatformModule.kt` | Koin module — binds `LoggerHelper` to `LoggerHelperImpl` |

## Internal Dependencies
- **None** — LEAF module, no project module dependencies (enforced by Popcorn Guineapig `NoDependencyRule`)

### Key External Libraries
- Compose Multiplatform (ui, foundation, material3)
- Jetpack Navigation Compose (navigation runtime, compose integration)
- Koin (core, coroutines, compose, compose-viewmodel)
- Kermit (logging)
- kotlinx-coroutines-core
- Android: Material (for `AndroidView` in VideoPlayer)

## External Dependencies (Consumers)
- `:composeApp` — depends on `platform` for `Screen` enum and `NavHostGraphBuilderExt`
- `:feature-movies`, `:feature-details`, `:feature-search`, `:feature-wishlist` — all extend `BaseViewModel` and use `PagingController`, `LoggerHelper`, navigation helpers

## Technical Notes
- **Popcorn Guineapig rule**: `NoDependencyRule` — must have NO project module dependencies
- `BaseViewModel.launchIo()` wraps all coroutine work in `runCatching`, rethrows `CancellationException`, and calls `onFailure(throwable)` for real exceptions
- `SimplePaging.currentPage` is exposed as a `StateFlow<Int>` consumed via `collectLatest` in ViewModels for pagination
- `VideoPlayer` uses `expect`/`actual` because Compose Multiplatform doesn't provide a built-in video player composable; the implementation embeds YouTube iframes in platform web views
- `LocalNavController` must be provided at a higher composition level (done in `RootApp.kt` in `composeApp`)

## Technical Debts
- `VideoPlayer` embeds YouTube via raw HTML iframes — cannot play non-YouTube videos, no play/pause controls, no full-screen support
- `LoggerHelper.plant(baseClass)` takes `KClass<*>` but stores via `simpleName` — different classes with the same simple name will collide
- `NavHostGraphBuilderExt` sanitizes arguments manually; consider using type-safe navigation with route classes
- The `MOVIE_DB_DOMAIN` constant used in iframe HTML is from the `platform` module, not from a configuration source
- **`BaseViewModel` has no tests in this module** — the MVI base is tested only indirectly through feature ViewModel tests. Direct tests for `launchIo` error routing and state flow behavior would improve confidence.
- **iOS `VideoPlayer` creates an unused `WKWebViewConfiguration` instance** — `remember { WKWebViewConfiguration() }` assigns a local variable that is never used, since properties are set directly on `webView.configuration`.
