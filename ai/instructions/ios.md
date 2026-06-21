# iOS Platform Instructions

## Entry Point

### MainViewController
`composeApp/src/iosMain/kotlin/.../MainViewController.kt`

```kotlin
fun MainViewController() = ComposeUIViewController {
    MovieDBAppTheme {
        RootApp()
    }
}
```

- Creates a `ComposeUIViewController` wrapping the shared `RootApp()` composable
- Applies `MovieDBAppTheme` for dark/light theming
- No Rinku deep link handler on iOS (Android-only)

### Koin Initialization (iOS)
- Koin is started via `initKoin()` before the ComposeUIViewController is created
- Uses the same `movieDbApplication {}` DSL as Android, without `androidContext()` and `analytics()`

## Key iOS-Specific Patterns

### Theming
- Uses the same `MovieDBAppTheme` composable from designsystem
- Custom amber/gold color schemes (`movieDBDarkColorScheme` / `movieDBLightColorScheme`)
- No dynamic colors support (Android-only feature)

### Deep Links
- Deep links handled by Rinku on Android only
- iOS uses standard navigation through Compose

### Database (Room)
- Uses `expect fun databaseInstance()` / `actual fun databaseInstance()` per platform
- iOS uses the Darwin engine for Ktor networking

### Networking (Ktor)
- Darwin engine for HTTP on iOS
- Same Ktor configuration shared across platforms via `expect`/`actual`

### Build Config
- TMDB API token from `local.properties` via BuildKonfig
- Same token flow as Android — BuildKonfig generates platform-specific config

## Xcode Project
`iosApp/iosApp.xcodeproj`

- Xcode project wraps the shared KMP framework
- Open with Xcode to build and run on iOS simulator
- No additional configuration needed beyond the shared KMP setup

## Build Commands

```bash
cd src
./gradlew :composeApp:compileKotlinDesktop    # Quick compilation check for shared code
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64  # Build iOS framework
```

To run on iOS: open `iosApp/iosApp.xcodeproj` in Xcode, select a simulator, and hit Run.
