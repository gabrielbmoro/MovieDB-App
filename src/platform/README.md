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
