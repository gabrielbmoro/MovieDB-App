# composeApp

## Purpose
UI orchestrator module — the central hub that assembles the navigation graph, aggregates all Koin DI modules, and hosts the shared `RootApp` composable consumed by both Android and iOS entry points.

## Primary Responsibility
- Owns the `NavHost` with all four screen routes: `Movies`, `Details/{movieId}`, `Search`, `Wishlist`
- Aggregates all Koin modules via `AppModules.kt` (`movieDbApplication` function)
- Handles deep link routing across platforms (`DeeplinkEffect.kt`)
- Serves as the single runtime dependency hub — depends on every other module so that downstream consumers (androidApp, iosApp) only need to depend on `composeApp`

## Existing Functionalities
- **Navigation graph** — Jetpack Navigation Compose `NavHost` in `RootApp.kt` wiring each route to its feature screen
- **DI aggregation** — starts Koin with data, platform, domain, and all feature modules via `lazyModules`
- **Deep link handling** — `DeeplinkEffect.kt` listens for Rinku `DeepLinkListener` events and emits routes for `movie/{id}`, `favorite`, `search?query=`
- **iOS Koin init** — `KoinHelper.kt` provides `initKoin()` called from Swift
- **iOS Compose bridge** — `MainViewController.kt` creates `ComposeUIViewController` wrapping `RootApp()` inside `MovieDBAppTheme`

## Internal Dependencies

| Dependency | Relationship |
|---|---|
| `:designsystem` | `api` (theme, shared UI components propagated) |
| `:platform` | `implementation` (navigation, ViewModel base, logging) |
| `:domain` | `implementation` (repository interfaces, use cases) |
| `:data` | `implementation` (DI module, repository impl) |
| `:feature-movies` | `implementation` (MoviesScreen) |
| `:feature-details` | `implementation` (DetailsScreen) |
| `:feature-search` | `implementation` (SearchScreen) |
| `:feature-wishlist` | `implementation` (WishlistScreen) |

### Key External Libraries
- Jetpack Navigation Compose, Rinku (deep links), Koin (core + coroutines)

## External Dependencies (Consumers)
- `androidApp` — depends on `composeApp` for the shared `RootApp()` composable
- `iosApp` — imports the `ComposeApp` framework (built from this module)

## Technical Notes
- `designsystem` is declared as `api` so all consumers transitively get the theme and shared UI components
- `LocalNavController` composition local is provided at this level via `CompositionLocalProvider`
- Koin uses `lazyModules()` for feature modules (lazy-loading) and eager modules for `data` and `platform`
- Deep links are Android-only (Rinku); iOS uses standard Compose navigation

## Technical Debts
- The `DeeplinkEffect` routes are duplicated across `DeeplinkEffect.kt` and `androidApp` manifest. Consider centralizing route definitions.
- `RootApp.kt` directly instantiates screens; consider a navigation-level use case or coordinator.
