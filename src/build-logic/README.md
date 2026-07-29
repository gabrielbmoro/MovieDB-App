# build-logic

## Purpose

Convention plugins Gradle module (included build) that provides shared build configuration to all other project modules. Defines and enforces build standards through precompiled script plugins and a programmatic Gradle plugin.

## Primary Responsibility

Ensures consistent project configuration across all modules — KMP targets, Android SDK settings, Detekt static analysis, Koin compiler setup, and Popcorn Guineapig architecture dependency rule enforcement.

## Existing Functionalities

- **`kmp-library-plugin`** — Applies KMP + Android Library configuration to any module that uses it. Sets Android `minSdk` (28), `compileSdk` (36), namespace generation (`com.gabrielbmoro.moviedb.<module-name>`), and iOS target registration (gated by `kmp.enableIos` property). Also applies `detekt-setup-plugin` automatically.
- **`detekt-setup-plugin`** — Applies Detekt with a base configuration overridden by custom rules at `$rootDir/config/detekt/detekt.yml`. Includes the `detekt-formatting` plugin. Sources scoped to `commonMain`, `androidMain`, `iosMain`.
- **`koin-compiler-setup`** — Programmatic plugin that applies the Koin Compiler Plugin (`io.insert-koin.compiler.plugin`) with `userLogs = true` and `compileSafety = true`. Adds `koin.core` and `koin.annotations` to `commonMain`. Replaces the older KSP-based Koin approach.
- **`popcorngp-setup-plugin`** — Applies Popcorn Guineapig with `KMP` project type. Enforces Clean Architecture dependency rules:
  - `:domain`, `:platform`, `:designsystem` — NoDependencyRule (zero project dependencies)
  - `:data` — JustWithRule (can only depend on `:domain`)
  - `:feature-*` — DoNotWithRule (must not depend on `:data`)
- **Configuration models** — Centralized constants: `APPLICATION_ID`, `APP_NAME`, `JavaConfiguration` (JVM 21), `SdkConfiguration` (minSdk=28, targetSdk=36, compileSdk=36)
- **Auto-versioning** — Reads `BITRISE_BUILD_NUMBER` from CI environment for version code/name; falls back to `10` / `"1.0.0"` locally, major version `"1.8"`
- **iOS target gating** — iOS targets (`iosX64`, `iosArm64`, `iosSimulatorArm64`) are only configured when Gradle property `kmp.enableIos` is set, enabling CI iOS builds without requiring macOS for every developer
- **Framework configuration** — iOS framework produced as a static framework named `ComposeApp`

## Entry Points

| Plugin | Plugin ID | File | Type |
|---|---|---|---|
| KMP Library | `kmp-library-plugin` | `plugins/kmp-library-plugin.gradle.kts` | Precompiled script |
| Detekt | `detekt-setup-plugin` | `plugins/detekt-setup-plugin.gradle.kts` | Precompiled script |
| Koin Compiler | `koin-compiler-setup` | `plugins/KoinCompilerSetupPlugin.kt` | Programmatic (`class`) |
| Popcorn Guineapig | `popcorngp-setup-plugin` | `plugins/popcorngp-setup-plugin.gradle.kts` | Precompiled script |

These plugins are applied by module `build.gradle.kts` files via the Gradle `plugins {}` DSL using the plugin IDs listed above.

## Important Workflows

### Module Build Configuration Flow
```
Module build.gradle.kts applies kmp-library-plugin
  → kmp-library-plugin applies:
      kotlin.multiplatform
      com.android.kotlin.multiplatform.library
      detekt-setup-plugin
  → kmp-library-plugin configures:
      Android SDK (minSdk=28, compileSdk=36)
      Namespace = com.gabrielbmoro.moviedb.<module>
      iOS targets (if kmp.enableIos=true)
      Static framework named "ComposeApp"
```

### Architecture Rule Enforcement (Popcorn Guineapig)
```
./gradlew popcornParent
  → Popcorn Guineapig scans all project modules
  → For each module matching a pattern, applies the associated rule
  → Failures are reported as build errors with the violating dependency
```

Rules applied by pattern:
- `:platform`, `:domain`, `:designsystem` → NoDependencyRule (must be leaf modules)
- `:data` → JustWithRule(justWith=["domain"])
- `:feature-[a-z]+` → DoNotWithRule(notWith=["data"])

### Koin Compiler Plugin Flow
```
Module applies koin-compiler-setup plugin
  → KoinCompilerSetupPlugin applies io.insert-koin.compiler.plugin
  → Configures KoinGradleExtension: userLogs=true, compileSafety=true
  → Adds koin.core + koin.annotations to commonMainImplementation
  → Koin compiler processes @Module, @Factory, @Single annotations at compile time
  → Generates module bindings without KSP
```

## Critical Files

| File | Role |
|---|---|
| `plugins/kmp-library-plugin.gradle.kts` | Base KMP + Android Library configuration applied by all modules |
| `plugins/detekt-setup-plugin.gradle.kts` | Detekt static analysis with custom rules |
| `plugins/KoinCompilerSetupPlugin.kt` | Programmatic plugin for Koin compiler (replaces KSP) |
| `plugins/popcorngp-setup-plugin.gradle.kts` | Architecture dependency rule enforcement |
| `config/ConfigurationKeys.kt` | Centralized constants (application ID, app name, SDK, Java versions) |
| `config/Versioning.kt` | CI-driven version code/name with local fallbacks |
| `ext/KotlinMultiPlatformExt.kt` | iOS target registration and framework configuration |
| `ext/ProjectExt.kt` | Version catalog accessor extension |
| `model/JavaConfiguration.kt` | JVM target data class (version, target string, JvmTarget) |
| `model/SdkConfiguration.kt` | Android SDK data class (min, target, compile) |

## Internal Dependencies

The `build-logic` included build has no dependencies on other project modules. It is a completely separate Gradle build that is included via `includeBuild("build-logic")` in `settings.gradle.kts`.

## External Dependencies

All declared in `build-logic/build.gradle.kts`:

| Dependency | Purpose |
|---|---|
| `kotlin-gradle-plugin` | Kotlin compiler for KMP |
| Android Gradle Plugin (`gradle`) | Android target configuration |
| KSP Gradle Plugin | Required by the classpath; Koin compiler replaces KSP, but the plugin is available |
| `popcorn-guineapig` | Architecture dependency rule enforcement |
| `koin-compiler-plugin` | Koin annotation processing at compile time |
| `detekt-gradle-plugin` | Static analysis |

## Related Modules

- **All project modules** depend on `build-logic` plugins — every `build.gradle.kts` applies at least `kmp-library-plugin` to inherit the base KMP + Android configuration
- **`:composeApp`** additionally applies `koin-compiler-setup` (for Koin annotation processing)
- **Root `build.gradle.kts`** applies `popcorngp-setup-plugin` at the project level to enforce architecture rules globally

## Technical Notes

- **Included build** — `build-logic` is an included build (`includeBuild("build-logic")` in settings), meaning its plugins are resolved before any project module builds
- **Precompiled script plugins** — `.gradle.kts` files in `src/main/kotlin/plugins/` are auto-discovered by the `kotlin-dsl-precompiled-script-plugins` plugin and become usable as plugin IDs
- **Programmatic plugin registration** — `KoinCompilerSetupPlugin` is registered in `build-logic/build.gradle.kts` via `gradlePlugin.plugins.create("koin-compiler-setup")`, mapping the plugin ID to the implementation class
- **Included build access** — modules access the `libs` version catalog from `build-logic` via the `val Project.libs` extension property
- **iOS conditional compilation** — iOS targets are gated by the Gradle property `kmp.enableIos`. When unset, iOS source sets are not compiled, allowing developers on non-macOS machines to build without errors. CI environments set this property.
- **Popcorn Guineapig task** — `popcornParent` runs as `./gradlew popcornParent` and reports violations as build failures

## Technical Debts

1. **No README existed for this module** — documentation gap filled as of this writing
2. **KSP plugin on classpath but unused** — Koin compiler replaced KSP for annotations, but the KSP Gradle plugin remains as a build-logic dependency; some modules still apply KSP directly for Room's KSP annotation processor (Room has no compiler plugin equivalent yet)
3. **Hardcoded major version** (`"1.8"`) in `Versioning.kt` — should be derived from a tag or a TOML property to avoid manual bumps
4. **`APP_NAME` constant used for both Android namespace and iOS framework name** — coupling that could break if one platform needs a different name
5. **No tests for convention plugins** — Gradle plugin behavior is verified only through full project builds (`./gradlew build`), not via unit tests for the plugins themselves
6. **iOS target configuration in `KotlinMultiPlatformExt.kt` assumes three specific iOS targets** — if KMP adds new iOS targets in future Kotlin versions, this file will need manual updates
