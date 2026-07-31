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

## Entry Points

| Entry Point | File | Signature | Role |
|---|---|---|---|
| Root composable | `src/commonMain/kotlin/RootApp.kt` | `@Composable fun RootApp()` | Builds `NavHost` with all 4 screen routes, provides `LocalNavController` |
| DI aggregator | `src/commonMain/kotlin/di/AppModules.kt` | `fun movieDbApplication(platformBlock: KoinApplication.() -> Unit): KoinApplication` | Starts Koin with all modules (data, platform, domain eagerly; features lazily) |
| Deep link effect | `src/commonMain/kotlin/DeeplinkEffect.kt` | `@Composable fun DeeplinkEffect()` | Listens for Rinku deep links, maps URI paths to navigation routes |
| iOS Koin init | `src/iosMain/kotlin/.../KoinHelper.kt` | `fun initKoin()` | iOS-side Koin initialization, called from Swift |
| iOS UI bridge | `src/iosMain/kotlin/.../MainViewController.kt` | `fun MainViewController(): UIViewController` | Creates `ComposeUIViewController` hosting the shared UI tree |

## Important Workflows

### DI Initialization Flow
```
App launch (Android: MovieDBApp.onCreate / iOS: iosAppApp.init)
  → movieDbApplication { }
    → Eager registration: dataModule, platformModule, DomainModule
    → Lazy registration: featureMoviesModule, featureDetailsModule,
      featureSearchMovieModule, featureWishlistModule
  → Koin container ready before any composable runs
```

### Navigation Wiring Flow
```
RootApp()
  → rememberNavController()
  → CompositionLocalProvider(LocalNavController provides navController)
    → DeeplinkEffect() starts listening for Rinku deep link events
    → NavHost(startDestination = Screen.Movies.route)
      → addMoviesScreen { MoviesScreen() }
      → addMovieDetailsScreen { movieId -> DetailsScreen(movieId) }
      → addSearchScreen { query -> SearchScreen(query) }
      → addWishlistScreen { WishlistScreen() }
```

### Deep Link Routing Flow
```
Rinku receives URI (e.g., movie://movie/123?query=batman)
  → DeepLinkListener callback with path segments
  → DeeplinkEffect matches firstSegment against Screen enum:
    - "movie" → extracts movieId from second segment → navigates to Details
    - "favorite" → navigates to Wishlist
    - "search" → extracts query parameter → navigates to Search
  → Unknown segments: logged via println (not LoggerHelper)
```

## Critical Files

| File | Role |
|---|---|
| `src/commonMain/kotlin/RootApp.kt` | Navigation graph assembly; provides `LocalNavController` |
| `src/commonMain/kotlin/di/AppModules.kt` | Koin DI bootstrap — the single aggregation point for all modules |
| `src/commonMain/kotlin/DeeplinkEffect.kt` | Rinku deep link → navigation route bridge |
| `src/iosMain/kotlin/.../KoinHelper.kt` | iOS Koin initialization entry point |
| `src/iosMain/kotlin/.../MainViewController.kt` | iOS ComposeUIViewController factory |

## Internal Dependencies

| Dependency | Relationship |
|---|---|
| `:designsystem` | `api` (theme, shared UI components propagated) |
| `:platform` | `implementation` (navigation, ViewModel base, logging) |
| `:domain` | `implementation` (repository interfaces, use cases) |
| `:data` | `implementation` (DI module, repository impl) |
| `:feature-movies` | `implementation` (MoviesScreen) |
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
- **Inconsistent package declarations**: `RootApp.kt` and `DeeplinkEffect.kt` have no package declaration; `AppModules.kt` uses `package di`; `MainViewController.kt` has no package. The project convention is `com.gabrielbmoro.moviedb.*`.
- **Raw `println` for logging**: `DeeplinkEffect.kt` line 59 uses `println("Not mapped")` instead of the project's standard `LoggerHelper` (Kermit) from `:platform`.
