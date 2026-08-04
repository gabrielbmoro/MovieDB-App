---
module: androidApp
summary: "Android entry point — Application subclass, ComponentActivity, deep link intent filters, dynamic colors, edge-to-edge."
keywords: [android, activity, application, manifest, deep-links, dynamic-colors, edge-to-edge, firebase, kotzilla]
---

# androidApp

## Purpose
Android application shell module — the thin platform-specific entry point that hosts the shared `RootApp` composable inside a `ComponentActivity`, initializes Koin DI, and wires up Android-specific services (deep links, dynamic colors, Firebase, Kotzilla analytics).

## Primary Responsibility
- Provide the Android `Application` subclass (`MovieDBApp`) for Koin and service initialization
- Host the shared Compose UI via `MainActivity` with `setContent { RootApp() }`
- Define the `AndroidManifest.xml` with permissions, deep link intent filters, and launcher activity declaration
- Apply Android-specific theming (Material You dynamic colors, edge-to-edge)

## Existing Functionalities
- **MovieDBApp** — `Application` subclass that calls `movieDbApplication { androidContext(this); analytics() }` to start Koin with the Android context and Kotzilla analytics
- **MainActivity** — `ComponentActivity` with `launchMode="singleTask"`; `setContent` wraps `RootApp()` inside:
  - `Rinku { }` — deep link handler composable
  - `DynamicColorApp { }` — applies Material You dynamic colors (Android 12+ / API 31+) or falls back to the custom theme
  - `enableEdgeToEdge()` — immersive edge-to-edge display
- **AndroidManifest.xml** — declares `INTERNET` + `ACCESS_NETWORK_STATE` permissions; deep link intent filter for `http(s)://themoviedb.org` paths (`/movies`, `/movie/*`, `/favorite`, `/search`)
- **network_security_config.xml** — blocks cleartext traffic in production; debug trusts user-installed certificates
- **Resources** — launcher icons (5 densities), `colors.xml` (light + dark), `styles.xml` (AppCompat DayNight NoActionBar), `strings.xml`

## Entry Points

| Entry Point | File | Signature | Role |
|---|---|---|---|
| Application | `src/main/kotlin/.../MovieDBApp.kt` | `class MovieDBApp : Application()` | Koin initialization, analytics setup |
| Activity | `src/main/kotlin/.../MainActivity.kt` | `class MainActivity : ComponentActivity()` | Hosts Compose UI with Rinku, dynamic colors, edge-to-edge |
| Manifest | `src/main/AndroidManifest.xml` | — | Permissions, deep link intent filters, launcher declaration |
| Theme wrapper | `src/main/kotlin/.../MainActivity.kt` | `@Composable private fun DynamicColorApp(...)` | Applies dynamic colors (API 31+) or custom theme fallback |

## Important Workflows

### App Launch on Android
```
Android system starts MainActivity (launchMode="singleTask")
  → MovieDBApp.onCreate(): movieDbApplication { androidContext(this); analytics() }
    → Koin started with Android context, dataModule, platformModule, DomainModule, lazy feature modules
    → Kotzilla analytics SDK initialized
  → MainActivity.setContent { Rinku { DynamicColorApp { enableEdgeToEdge(); RootApp() } } }
    → Rinku wraps composable tree for deep link interception
    → DynamicColorApp: checks Build.VERSION.SDK_INT >= 31
      → API 31+: generates dynamicDarkColorScheme() or dynamicLightColorScheme()
      → Below API 31: uses movieDBDarkColorScheme / movieDBLightColorScheme from :designsystem
    → enableEdgeToEdge(): immersive display (status bar + nav bar transparent)
    → RootApp() from :composeApp renders the full navigation graph
```

### Deep Link Handling (Android)
```
User taps https://themoviedb.org/movie/123
  → AndroidManifest intent-filter matches the URI pattern
  → MainActivity receives the intent (singleTask reuses existing instance)
  → Rinku intercepts the deep link → forwards to DeeplinkEffect in :composeApp
  → DeeplinkEffect maps path segments to navigation routes
```

## Critical Files

| File | Role |
|---|---|
| `src/main/kotlin/.../MovieDBApp.kt` | Application subclass — Koin + analytics bootstrap |
| `src/main/kotlin/.../MainActivity.kt` | ComponentActivity — Compose host, Rinku, dynamic colors, edge-to-edge |
| `src/main/AndroidManifest.xml` | Permissions, deep link intent-filters, launchMode |
| `src/main/res/values/strings.xml` | App name and (legacy) hardcoded TMDB token |
| `src/main/res/xml/network_security_config.xml` | Cleartext blocking in release; user cert trust in debug |

## Internal Dependencies

| Dependency | Relationship |
|---|---|
| `:composeApp` | `implementation` (RootApp, DI aggregation, all feature screens) |

### Key External Libraries
- Jetpack Compose (activity-compose, Material3, Compose Multiplatform bundles), Koin (android + coroutines), Rinku (deep links), Kotzilla (analytics), Firebase Crashlytics, Google Services

## External Dependencies (Consumers)
- None — `androidApp` is a leaf module with no downstream consumers. It is the final deployable Android application artifact.

## Technical Notes
- `DynamicColorApp` is a private `@Composable` defined in `MainActivity.kt` — it checks `Build.VERSION.SDK_INT >= S` before attempting to generate dynamic color schemes.
- `launchMode="singleTask"` ensures the activity is reused when deep links are opened, preventing duplicate instances.
- Release signing is configured via Bitrise environment variables (`BITRISE_ANDROID_KEYSTORE_*`), not checked into the repository.
- ProGuard is referenced in `build.gradle.kts` but `isMinifyEnabled = false` — minification is currently disabled for both debug and release builds.
- Firebase Crashlytics and Kotzilla analytics are configured via `google-services.json` and `kotzilla.json` respectively.

## Technical Debts
- **Hardcoded TMDB API token**: `strings.xml` contains a hardcoded `token_value` (`755e0c67ac2fa886e775fb9057f0a32f`) — a v3 auth key exposed in source. This should be moved to `local.properties` / BuildKonfig or removed entirely (the app uses Bearer token auth via BuildKonfig).
- **No tests**: this module has zero test files (no `src/test/` or `src/androidTest/`). While intentionally thin, instrumentation tests for deep link routing and activity launch should be considered.
- **`isMinifyEnabled = false`** for release builds — no R8 obfuscation, shrinking, or optimization is applied to the final APK/AAB.
- **`DynamicColorApp`** is a private composable in `MainActivity.kt` — could be extracted to a separate file or moved to `designsystem` for reuse across platforms.
