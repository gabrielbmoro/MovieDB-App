# Android Platform Instructions

## Entry Points

### Application class
`androidApp/src/main/kotlin/.../MovieDBApp.kt`

```kotlin
class MovieDBApp : Application() {
    override fun onCreate() {
        super.onCreate()
        movieDbApplication {
            androidContext(this@MovieDBApp)
            analytics()
        }
    }
}
```

- Extends `Application` — Koin is initialized here via `movieDbApplication {}` DSL
- `androidContext()` wires Android context into Koin
- `analytics()` enables Kotzilla SDK analytics

### MainActivity
`androidApp/src/main/kotlin/.../MainActivity.kt`

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Rinku {                    // Deep link handler
                DynamicColorApp {      // Android 12+ dynamic colors
                    enableEdgeToEdge()
                    RootApp()
                }
            }
        }
    }
}
```

- `Rinku` — wraps the composable tree for deep link handling (URIs: `movie/{id}`, `search?query=`, `favorite`)
- `DynamicColorApp` — applies Material 3 dynamic colors on API 31+, falls back to `movieDBDarkColorScheme` / `movieDBLightColorScheme`
- `enableEdgeToEdge()` — immersive full-screen display

## Key Android-Specific Patterns

### Theming
- Material 3 with Android 12+ dynamic color support via `dynamicDarkColorScheme()` / `dynamicLightColorScheme()`
- Fallback: custom amber/gold color schemes from `designsystem/theme/Color.kt`
- Wrapped in `MovieDBAppTheme` composable

### Deep Links
- Rinku library handles deep link URIs
- Routes: `movie/{id}` → Details screen, `search?query=` → Search screen, `favorite` → Wishlist screen
- Navigation graph built in `RootApp.kt` using Jetpack Navigation Compose

### Crash Reporting
- Firebase Crashlytics for crash reporting

### Build Config
- TMDB API token from `src/local.properties` → `MOVIE_DB_API_TOKEN`
- Injected at build time via BuildKonfig

### DI (Koin)
- `lazyModules()` for feature module loading
- `@Module`, `@Factory`, `@Single` annotations with KSP + Koin Compiler plugin

### Permissions
- Internet permission required for TMDB API access
- No other special permissions needed

## Build Commands

```bash
cd src
./gradlew :androidApp:assembleDebug      # Build Android debug APK
./gradlew :composeApp:connectedCheck     # Run instrumentation tests
./gradlew detektAll                      # Run Detekt linting
```
