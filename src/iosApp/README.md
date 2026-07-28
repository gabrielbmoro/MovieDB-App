# iosApp

## Purpose

iOS application shell that wraps the shared Compose Multiplatform UI (`:composeApp` module) inside a native SwiftUI application. Serves as the iOS deployment artifact.

## Primary Responsibility

Platform entry point for iOS — bootstraps Koin dependency injection, builds the Kotlin Multiplatform framework via Gradle, and hosts the Compose UI tree inside a `UIViewControllerRepresentable`.

## Existing Functionalities

- **App Launch** (`iosAppApp.swift`) — `@main` entry point that initializes Koin via `KoinHelperKt.doInitKoin()` before the UI renders
- **Compose Bridge** (`ContentView.swift`) — `ComposeView` struct implementing `UIViewControllerRepresentable` that calls `MainViewControllerKt.MainViewController()` to create the shared Compose UI
- **Framework Build Integration** — Xcode build phase script executes `./gradlew :composeApp:embedAndSignAppleFrameworkForXcode` to compile the KMP framework before the Swift linker runs
- **App Icon** — 12 icon variants across all required iPhone/iPad scales plus 1024x1024 App Store icon
- **Edge-to-Edge** — `.ignoresSafeArea(.container)` and `.ignoresSafeArea(.keyboard)` for full-screen rendering (Compose handles insets internally)
- **High Refresh Rate** — `CADisableMinimumFrameDurationOnPhone: true` in `Info.plist` enables ProMotion display support

## Entry Points

| Entry Point | File | Signature | Role |
|---|---|---|---|
| App launch | `iosApp/iosAppApp.swift` | `@main struct iosAppApp: App` | Application entry, Koin init |
| Compose bridge | `iosApp/ContentView.swift` | `struct ComposeView: UIViewControllerRepresentable` | Wraps Compose UI in SwiftUI |
| Content wrapper | `iosApp/ContentView.swift` | `struct ContentView: View` | Root SwiftUI View |
| Build phase | `iosApp.xcodeproj/project.pbxproj` | Shell script build phase | Gradle framework compilation |

## Important Workflows

### App Launch Sequence
```
iosAppApp.init()
  → KoinHelperKt.doInitKoin()           // Starts Koin DI container (shared code)
    → movieDbApplication { }             // Registers dataModule, platformModule, DomainModule, all feature lazyModules
  → ContentView.body
    → ComposeView (UIViewControllerRepresentable)
      → makeUIViewController(context:)
        → MainViewControllerKt.MainViewController()   // Shared KMP function
          → ComposeUIViewController { MovieDBAppTheme { RootApp() } }
```

### Build-Time Workflow
```
Xcode build triggers "Compile Kotlin" phase
  → cd $SRCROOT/..
  → ./gradlew :composeApp:embedAndSignAppleFrameworkForXcode
    → Compiles composeApp KMP module for iosX64, iosArm64, iosSimulatorArm64
    → Produces static framework at composeApp/build/xcode-frameworks/<config>/<sdk>/
  → Framework linked into iOS app binary
```

Environment variable `OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED=YES` can be set to skip the Gradle build phase (useful when Kotlin code hasn't changed).

## Critical Files

| File | Role |
|---|---|
| `iosApp/iosAppApp.swift` | `@main` entry point, Koin initialization |
| `iosApp/ContentView.swift` | SwiftUI wrapper for Compose UI via `UIViewControllerRepresentable` |
| `iosApp/Info.plist` | Bundle configuration: display name, ProMotion support |
| `iosApp/Assets.xcassets/AppIcon.appiconset/` | App icon assets (12 sizes) |
| `iosApp.xcodeproj/project.pbxproj` | Xcode project: build phases, framework search paths, signing |

## Internal Dependencies

Depends on `:composeApp` — both as a Gradle project dependency (framework compilation) and as a Swift `import ComposeApp` in source files. The framework is embedded during the Xcode build phase.

| Dependency | Type | Purpose |
|---|---|---|
| `:composeApp` | Framework dependency | Shared Compose UI (imported as `ComposeApp`) |

## External Dependencies

None beyond Apple platform SDKs (SwiftUI, UIKit, WebKit — the last via the shared KMP framework's `VideoPlayer`).

## Related Modules

- **`:composeApp`** — Provides `MainViewController` and `KoinHelper` (iOS source set) exposed to Swift via `MainViewControllerKt` and `KoinHelperKt`. The iOS app is the only consumer of this module's `iosMain` source set.
- **`:platform`** — Provides `VideoPlayer` composable (expect/actual) whose iOS actual uses `WKWebView`, which is linked through the shared framework.

## Technical Notes

- **Framework type:** Static framework named `ComposeApp` (configured in `KotlinMultiPlatformExt.kt` using `ConfigurationKeys.APP_NAME`)
- **Deployment target:** iOS 17.4
- **Swift version:** 5.0
- **Xcode object version:** 56 (compatible with Xcode 14.0 minimum)
- **Bundle ID:** `com.gabrielbmoro.moviedb.iosApp` — distinct from Android's `com.gabrielbmoro.moviedb`
- **Target devices:** iPhone + iPad (`TARGETED_DEVICE_FAMILY = "1,2"`)
- **Build configurations:** Debug and Release (Default: Release)
- **Signing:** Development team `P8ZZXHC5BB`; release signing managed through Xcode
- **Koin initialization** happens in Swift (`iosAppApp.init()`) before any Compose code runs, ensuring DI is ready when `MainViewController()` creates the Compose tree
- **No deep links** on iOS — the Rinku library and `DeeplinkEffect` composable are Android-only. iOS navigation relies solely on in-app interaction

## Technical Debts

1. **No README existed for this module** — documentation gap filled as of this writing
2. **Hardcoded development team** (`P8ZZXHC5BB`) in Xcode project — should use CI-configured code signing for reproducible builds
3. **No iOS-specific tests** — the entire iOS target is tested only indirectly through shared KMP tests
4. **Build phase runs Gradle on every Xcode build** — `OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED` workaround is manual; consider adopting KMP's built-in framework embedding
5. **Marketing version hardcoded to 1.0** — should be derived from CI/build pipeline, not committed in the project file
