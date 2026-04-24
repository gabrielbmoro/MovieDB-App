# MovieDB-App — AI Context

## Project Overview

Kotlin Multiplatform (KMP) app using **Compose Multiplatform** targeting Android and iOS. Displays movie data from The Movie Database (TMDB) API with features like browsing, searching, favoriting, and viewing details.

- **Kotlin:** 2.3.20
- **Compose Multiplatform:** 1.10.3
- **AGP:** 9.1.0
- **Min SDK:** 28 | **Target/Compile SDK:** 36
- **Java / JVM Target:** 21
- **Package:** `com.gabrielbmoro.moviedb`

---

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
| DI | Koin (annotations + KSP) | 4.2.1 |
| Koin Annotations | KSP compiler for `@Module`/`@Factory`/`@Single` | 2.3.1 |
| State | Coroutines + StateFlow | 1.10.2 |
| Collections | kotlinx-collections-immutable | 0.4.0 |
| Logging | Kermit | 2.1.0 |
| Deep Links | Rinku | 1.6.0 |
| Build Config | BuildKonfig | 0.18.0 |
| Dependency Audit | Popcorn Guineapig | 3.1.6 |
| Linting | Detekt | 1.23.8 |
| Coverage | Kover | 0.9.8 |
| Crash Reporting | Firebase Crashlytics | — |
| Analytics | Kotzilla | 2.0.8 |
| CI | Bitrise + GitHub Actions | — |

---

## Architecture: Clean Architecture + MVI

### Layer Dependency Rules (enforced by Popcorn Guineapig)

```
composeApp (UI orchestrator)
    │
    ├── feature:*      → depends on domain, designsystem, platform
    ├── domain         → LEAF — no project module dependencies
    ├── data           → depends ONLY on domain (implements repository interfaces)
    ├── designsystem   → depends only on util:media
    ├── platform       → navigation, paging, MVI base
    └── util:*         → LEAF — no project module dependencies
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

---

## Module Structure

```
src/
├── composeApp/          # NavHost, DI aggregator, RootApp.kt
├── data/                # ApiService, DTOs, DAOs, DatabaseProvider
├── domain/              # Entities, Repository interface, UseCases, Mappers
├── designsystem/        # Theme, Colors, shared UI (cards, toolbars, icons)
├── platform/            # Navigation (Screen enum, extensions), PagingController, MVI base
├── feature/
│   ├── feature-movies/  # Movie grid with filter tabs + pagination
│   ├── feature-details/ # Movie detail (backdrop, rating, favorite, info)
│   ├── feature-search/  # Debounced search with results
│   └── feature-wishlist/# Favorites list with swipe-to-delete
├── util/
│   ├── media/           # AsyncImage (Coil), VideoPlayer (expect/actual)
│   └── logging/         # Kermit wrapper
├── androidApp/          # Android entry (Application, MainActivity)
├── iosApp/              # Xcode project
└── build-logic/         # Convention plugins (kmp-library, koin-ksp, popcorngp)
```

---

## Navigation

- **Type:** Jetpack Navigation Compose Multiplatform
- **Routes** (`Screen` enum in `platform` module): `Movies`, `Details/{movieId}`, `Search`, `Wishlist`
- **NavHostController** exposed via `CompositionLocal` (`LocalNavController`)
- **Deep links:** Rinku library handles `movie/{id}`, `search?query=`, `favorite` URIs
- Navigation graph built in `RootApp.kt` using extension functions from `NavHostGraphBuilderExt.kt`

---

## State Management (MVI)

Each feature screen follows the **Model-View-Intent** pattern:

- **Model:** sealed interface for intents (e.g., `MoviesIntent`, `DetailsUserIntent`)
- **View:** data class for UI state (e.g., `MoviesState`, `DetailsUIState`)
- **Intent:** ViewModel extends `ViewModelMvi<UserIntent>` with `execute(intent: UserIntent)`

Base interface (`ViewModelMvi`):
```kotlin
interface ViewModelMvi<in UserIntent> {
    fun execute(intent: UserIntent)
}
```

ViewModels expose state via `MutableStateFlow` + `stateIn(viewModelScope, ...)`.
Screens consume state with `collectAsState()` and dispatch intents.

---

## Dependency Injection (Koin)

- **Koin Annotations** (`@Module`, `@Factory`, `@Single`) with KSP
- Each layer declares its own module: `DataModule`, `DomainModule`, feature modules
- Feature modules include `DomainModule` (which includes `DataModule`)
- Android: Koin started in `MovieDBApp.onCreate()` with `lazyModules()`
- iOS: Koin started in `KoinHelper.initKoin()`

---

## API (TMDB)

- **Base URL:** `https://api.themoviedb.org/3`
- **Auth:** Bearer token (from `local.properties` via BuildKonfig)
- **HTTP Client:** Ktor with content negotiation + logging
- **Endpoints:**
  - `GET /movie/{category}?page={n}` — listings (popular, top_rated, upcoming, now_playing)
  - `GET /movie/{id}` — details
  - `GET /movie/{id}/videos` — video streams
  - `GET /search/movie?query={q}` — search

---

## Database (Room)

- Entity: `FavoriteMovieDTO` (table: `favorite_movies`)
- DAO: `FavoriteMoviesDAO`
- DB init: `expect fun databaseInstance()` / `actual fun databaseInstance()` per platform

---

## Coding Conventions

- **Package naming:** `com.gabrielbmoro.moviedb.<module>`
- **Screen pattern:** `*Screen.kt` (composable) + `*ViewModel.kt` + `*Intent` + `*State`
- **Widgets:** feature-level reusable composables in `ui/widgets/`
- **DI:** Koin `@Module` per module, `@Factory` for ViewModels, `@Single` for singletons
- **State:** `StateFlow` in ViewModels, `collectAsState()` in composables
- **Pagination:** `SimplePaging` via `PagingController` (`requestNextPage()` / `resetPaging()`)
- **Image loading:** `AsyncImage` from `util:media` (Coil wrapper)
- **Logging:** `LoggerHelper` from `util:logging` (Kermit wrapper)
- **Testing:** JUnit + kotlin-test + kotlinx-coroutines-test

---

## Theme / Styling

- **Material 3** with custom dark/light color schemes (amber/gold primary)
- `MovieDBAppTheme` composable wraps `MaterialTheme`
- Android 12+: Dynamic Colors via `dynamicDarkColorScheme` / `dynamicLightColorScheme`
- Fallback: `movieDBDarkColorScheme` / `movieDBLightColorScheme` (defined in `designsystem/theme/Color.kt`)
- Android: `enableEdgeToEdge()` for immersive display

---

## Key Entry Points

| Platform | File | What it does |
|---|---|---|
| Android | `MainActivity.kt` | Sets up theme, Rinku deep links, hosts `RootApp()` |
| iOS | `MainViewController.kt` | Creates `ComposeUIViewController` with `RootApp()` |
| Shared | `RootApp.kt` | Creates `NavHost` with all routes |
| Shared | `AppModules.kt` | Aggregates all Koin modules |

---

## Project Files of Interest

| File | Purpose |
|---|---|
| `src/settings.gradle.kts` | Module includes, Kover coverage config |
| `src/build.gradle.kts` | Root build — aggregates Kover, Detekt report |
| `src/gradle/libs.versions.toml` | Version catalog (all deps) |
| `src/build-logic/` | Convention plugins (KMP, Koin KSP, Popcorn GP) |
| `src/config/detekt/detekt.yml` | Linting rules |
| `src/gradle.properties` | KMP / Android SDK settings |
| `renovate.json` | Automated dependency updates |

---

## Dependency Rules (Popcorn Guineapig)

Enforced by `build-logic/src/main/kotlin/plugins/popcorngp-setup-plugin.gradle.kts`:

| Module Pattern | Rule | Detail |
|---|---|---|
| `:util:*` | `NoDependencyRule` | Must have NO project module dependencies |
| `:feature:*` | `DoNotWithRule(notWith=["data"])` | Must NOT depend on `:data` |
| `:domain` | `NoDependencyRule` | Must have NO project module dependencies |
| `:data` | `JustWithRule(justWith=["domain"])` | Can ONLY depend on `:domain` |
| `:designsystem` | `JustWithRule(justWith=["media"])` | Can ONLY depend on `:util:media` |

The runtime dependency direction is: **data → domain** (data implements domain's repository interfaces). Domain is a pure business-logic leaf with no knowledge of data sources.
