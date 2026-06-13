# MovieDB-App

## Project Overview

Kotlin Multiplatform (KMP) app using **Compose Multiplatform** targeting Android and iOS. Displays movie data from The Movie Database (TMDB) API.

- **Kotlin:** 2.3.20
- **Compose Multiplatform:** 1.10.3
- **AGP:** 9.1.0
- **Gradle:** 9.4.1
- **Min SDK:** 28 | **Target/Compile SDK:** 36
- **Java / JVM Target:** 21
- **Package:** `com.gabrielbmoro.moviedb`

## Build Commands

All projects live under `src/`. Run commands from the `src/` directory.

| Command | Description |
|---|---|
| `./gradlew composeApp:compileKotlinDesktop` | Quick Kotlin compilation check |
| `./gradlew :composeApp:connectedCheck` | Run instrumentation tests |
| `./gradlew detektAll` | Run Detekt linting |
| `./gradlew koverHtmlReportAll` | Generate Kover coverage report |
| `./gradlew :build-logic:checkPopcornGuineapig` | Verify module dependency rules |
| `./gradlew build` | Full build |

## Architecture: Clean Architecture + MVI

### Layer Dependency Rules (enforced by Popcorn Guineapig)

```
composeApp (UI orchestrator)
    |
    ├── feature:*      → depends on domain, designsystem, platform
    ├── domain         → LEAF — no project module dependencies
    ├── data           → depends ONLY on domain (implements repository interfaces)
    ├── designsystem   → LEAF — no project module dependencies
    ├── platform       → LEAF — no project module dependencies
```

### Data Flow

```
UI (Screen composable)
  → ViewModel (intent → state via StateFlow)
    → UseCase (business logic)
      → Repository interface (in domain)
        → MoviesDataRepository (in data)
          → ApiService (Ktor → TMDB API)
          → FavoriteMoviesDAO (Room → SQLite)
```

## Tech Stack

| Category | Library | Version |
|---|---|---|
| UI | Compose Multiplatform + Material3 | 1.10.3 / 1.9.0 |
| Compose Compiler Plugin | Bundled with Kotlin compiler | 2.3.20 |
| Navigation | Jetpack Navigation Compose | 2.9.2 |
| Lifecycle | lifecycle-viewmodel-compose | 2.10.0 |
| Networking | Ktor (OkHttp/Darwin engines) | 3.4.2 |
| Serialization | kotlinx-serialization | — |
| Image Loading | Coil 3 (ktor3 network) | 3.4.0 |
| Database | Room + sqlite-bundled | 2.8.4 / 2.6.2 |
| DI | Koin (annotations + KSP + Koin Compiler) | 4.2.1 |
| Koin Annotations | KSP compiler for `@Module`/`@Factory`/`@Single` | 2.3.1 |
| Koin Compiler Plugin | Custom convention plugin for Koin compiler | 1.0.0 |
| State | Coroutines + StateFlow | 1.10.2 |
| Collections | kotlinx-collections-immutable | 0.4.0 |
| Logging | Kermit | 2.1.0 |
| Deep Links | Rinku | 1.6.0 |
| Build Config | BuildKonfig | 0.18.0 |
| Dependency Audit | Popcorn Guineapig | 3.2.0 |
| Linting | Detekt | 1.23.8 |
| Coverage | Kover | 0.9.8 |
| Crash Reporting | Firebase Crashlytics | — |
| Analytics | Kotzilla | 2.0.8 |
| CI | Bitrise + GitHub Actions | — |

Dependency versions are sourced from `src/gradle/libs.versions.toml`.

## Module Structure

```
src/
├── composeApp/          # NavHost, DI aggregator, RootApp.kt
├── data/                # ApiService, DTOs, DAOs, DatabaseProvider, Mappers
├── domain/              # Domain models, Repository interfaces, UseCases
├── designsystem/        # Theme, Colors, shared UI (cards, toolbars, icons, AsyncImage)
├── platform/            # Navigation, PagingController, BaseViewModel, Logging, VideoPlayer
├── feature-movies/      # Movie grid with filter tabs + pagination
├── feature-details/     # Movie detail (backdrop, rating, favorite, info)
├── feature-search/      # Debounced search with results
├── feature-wishlist/    # Favorites list with swipe-to-delete
├── androidApp/          # Android entry (Application, MainActivity)
├── iosApp/              # Xcode project
└── build-logic/         # Convention plugins (kmp-library, koin-compiler, popcorngp)
```

## Navigation

- **Type:** Jetpack Navigation Compose Multiplatform
- **Routes** (`Screen` enum in `platform` module): `Movies`, `Details/{movieId}`, `Search`, `Wishlist`
- **NavHostController** exposed via `CompositionLocal` (`LocalNavController`)
- **Deep links:** Rinku library handles `movie/{id}`, `search?query=`, `favorite` URIs
- Navigation graph built in `RootApp.kt` using extension functions from `NavHostGraphBuilderExt.kt`

## State Management (MVI)

Each feature screen follows the **Model-View-Intent** pattern via `BaseViewModel`:

```kotlin
abstract class BaseViewModel<State : UiState, Intent : UserIntent, Event : UiEvent>
```

- **State:** data class implementing `UiState` interface
- **Intent:** sealed class/interface implementing `UserIntent`
- **Event:** sealed class/interface implementing `UiEvent`
- ViewModel exposes `uiState: StateFlow<State>` and `uiEvent: SharedFlow<Event>`
- Screens consume state with `collectAsState()` and dispatch intents via `executeIntent()`
- Background work launched with `launchIo()` which catches errors and routes to `onFailure()`

## Dependency Injection (Koin)

- **Koin Annotations** (`@Module`, `@Factory`, `@Single`) with KSP + Koin Compiler plugin
- Custom `KoinCompilerSetupPlugin` convention plugin applies the Koin compiler and configures it
- Each layer declares its own module: `DataModule`, `DomainModule`, feature modules
- Feature modules include `DomainModule` (which includes `DataModule`)
- Android: Koin started in `MovieDBApp.onCreate()` with `lazyModules()`
- iOS: Koin started in `KoinHelper.initKoin()`

## API (TMDB)

- **Base URL:** `https://api.themoviedb.org/3`
- **Auth:** Bearer token (from `local.properties` via BuildKonfig)
- **HTTP Client:** Ktor with content negotiation + logging
- **Endpoints:**
  - `GET /movie/{category}?page={n}` — listings (popular, top_rated, upcoming, now_playing)
  - `GET /movie/{id}` — details
  - `GET /movie/{id}/videos` — video streams
  - `GET /search/movie?query={q}` — search

## Database (Room)

- Entity: `FavoriteMovieDTO` (table: `favorite_movies`)
- DAO: `FavoriteMoviesDAO`
- DB init: `expect fun databaseInstance()` / `actual fun databaseInstance()` per platform

## Coding Conventions

- **Package naming:** `com.gabrielbmoro.moviedb.<module>` for non-feature modules; `com.gabrielbmoro.moviedb.feature.<name>` for features
- **Screen pattern:** `*Screen.kt` (composable) + `*ViewModel.kt` + intent/state/event models
- **Widgets:** feature-level reusable composables in `ui/widgets/`
- **DI:** Koin `@Module` per module, `@Factory` for ViewModels, `@Single` for singletons
- **State:** `StateFlow` in ViewModels, `collectAsState()` in composables
- **Pagination:** `SimplePaging` via `PagingController` (`requestNextPage()` / `resetPaging()`)
- **Image loading:** `AsyncImage` from `designsystem` (Coil wrapper)
- **Logging:** `LoggerHelper` from `platform` (Kermit wrapper)
- **Error handling:** `HttpException` model in domain; `ErrorScreen`/`ErrorInfo` components in designsystem
- **Testing:** JUnit + kotlin-test + kotlinx-coroutines-test
- **Domain models:** in `domain/model/`
- **Mappers:** in `data/repository/mappers/`

## Theme / Styling

- **Material 3** with custom dark/light color schemes (amber/gold primary)
- `MovieDBAppTheme` composable wraps `MaterialTheme`
- Android 12+: Dynamic Colors via `dynamicDarkColorScheme` / `dynamicLightColorScheme`
- Fallback: `movieDBDarkColorScheme` / `movieDBLightColorScheme` (defined in `designsystem/theme/Color.kt`)
- Android: `enableEdgeToEdge()` for immersive display

## Key Entry Points

| Platform | File | What it does |
|---|---|---|
| Android | `MainActivity.kt` | Sets up theme, Rinku deep links, hosts `RootApp()` |
| iOS | `MainViewController.kt` | Creates `ComposeUIViewController` with `RootApp()` |
| Shared | `RootApp.kt` | Creates `NavHost` with all routes |
| Shared | `AppModules.kt` | Aggregates all Koin modules |

## Project Files of Interest

| File | Purpose |
|---|---|
| `src/settings.gradle.kts` | Module includes, Kover coverage config |
| `src/build.gradle.kts` | Root build — aggregates Kover, Detekt report |
| `src/gradle/libs.versions.toml` | Version catalog (all deps) |
| `src/build-logic/` | Convention plugins (KMP, Koin Compiler, Popcorn GP) |
| `src/build-logic/src/main/kotlin/plugins/KoinCompilerSetupPlugin.kt` | Custom Koin compiler convention plugin |
| `src/config/detekt/detekt.yml` | Linting rules |
| `src/gradle.properties` | KMP / Android SDK settings |
| `renovate.json` | Automated dependency updates |
| `opencode.json` | OpenCode MCP configuration |

## Dependency Rules (Popcorn Guineapig)

Enforced by `build-logic/src/main/kotlin/plugins/popcorngp-setup-plugin.gradle.kts`:

| Module Pattern | Rule | Detail |
|---|---|---|
| `:platform` | `NoDependencyRule` | Must have NO project module dependencies |
| `:feature-[a-z]+` | `DoNotWithRule(notWith=["data"])` | Must NOT depend on `:data` |
| `:domain` | `NoDependencyRule` | Must have NO project module dependencies |
| `:data` | `JustWithRule(justWith=["domain"])` | Can ONLY depend on `:domain` |
| `:designsystem` | `NoDependencyRule` | Must have NO project module dependencies |

## Testing Standards

- Framework: `kotlin.test` (`@Test`, `@BeforeTest`, `@AfterTest`)
- Coroutines: `kotlinx-coroutines-test` (`StandardTestDispatcher`, `runTest`, `advanceUntilIdle`)
- No mocking frameworks — use hand-written fakes (`FakeRepository`, `FakeUseCase`)
- Tests located in `src/commonTest/` per module
- Test dependencies: `kotlin_test`, `kotlin_test_common`, `kotlinx_coroutines_test` from version catalog

## MVI Pattern Per Feature

Each feature should have:
- **`Model.kt`** — sealed interface for user intents, data class for UI state
- **`*ViewModel.kt`** — extends `ViewModel` + `ViewModelMvi<UserIntent>`; exposes state via `StateFlow` using `stateIn(viewModelScope, SharingStarted.Eagerly, ...)`
- **`*Screen.kt`** — Composable that collects state via `collectAsState()` and dispatches intents via `viewModel.execute(intent)`

## Error Handling (Presentation Layer)

- Every suspend function call to a repository or use case inside a ViewModel or Handler **must** be wrapped in `runCatching { }`
- The `runCatching` block must handle both success and failure:
  - **Success**: update UI state with the result
  - **Failure**: call `loggerHelper.logError(error)` and update UI state (set `isLoading = false`, surface `errorMessage` to the user)
- UI state models should include an `errorMessage: String?` field to surface errors to the user
- Exceptions must never be silently swallowed via `.getOrNull()` without a fallback state update
- `!!` null-forced expressions on ViewModel fields are not acceptable — use safe calls or `?: return`
- Every `viewModelScope.launch` block must be protected — an unhandled exception silently kills the coroutine and all future collection
