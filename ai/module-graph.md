# Module Dependency Graph

Enforced by Popcorn Guineapig (`build-logic/src/main/kotlin/plugins/popcorngp-setup-plugin.gradle.kts`).

## Layers

```
composeApp (UI orchestrator — NavHost, DI aggregation, RootApp.kt)
    |
    ├── feature-movies      → domain, designsystem, platform
    ├── feature-details     → domain, designsystem, platform
    ├── feature-search      → domain, designsystem, platform
    ├── feature-wishlist    → domain, designsystem, platform
    │
    ├── domain              → LEAF — no project module dependencies
    ├── data                → depends ONLY on domain
    ├── designsystem        → LEAF — no project module dependencies
    ├── platform            → LEAF — no project module dependencies
    │
    ├── androidApp          → composeApp (entry point)
    ├── iosApp              → Xcode project (wraps composeApp)
    └── build-logic         → convention plugins (not a runtime dependency)
```

## Dependency Rules

| Module Pattern | Rule | Detail |
|---|---|---|
| `:domain` | NoDependencyRule | Must have NO project module dependencies |
| `:data` | JustWithRule(justWith=["domain"]) | Can ONLY depend on `:domain` |
| `:designsystem` | NoDependencyRule | Must have NO project module dependencies |
| `:platform` | NoDependencyRule | Must have NO project module dependencies |
| `:feature-[a-z]+` | DoNotWithRule(notWith=["data"]) | Must NOT depend on `:data` |

## Verify

```bash
cd src && ./gradlew :build-logic:checkPopcornGuineapig
```

## Module Responsibilities

| Module | What it owns |
|---|---|
| `domain` | Domain models, repository interfaces, use cases |
| `data` | ApiService (Ktor), DTOs, DAOs (Room), DatabaseProvider, mappers, repository implementations |
| `designsystem` | Theme (colors, typography, shapes), shared UI composables (cards, toolbars, AsyncImage, ErrorScreen) |
| `platform` | Navigation (Screen enum, NavController), PagingController, BaseViewModel, LoggerHelper, VideoPlayer |
| `feature-movies` | Movie grid with category filter tabs + pagination |
| `feature-details` | Movie detail (backdrop, rating, favorite toggle, info) |
| `feature-search` | Debounced search with inline results |
| `feature-wishlist` | Favorites list with swipe-to-delete |
| `composeApp` | NavHost, RootApp.kt, DI aggregation (AppModules) |
| `androidApp` | Android Application class, MainActivity, Rinku deep links |
| `iosApp` | Xcode project, KoinHelper, MainViewController |
| `build-logic` | Convention plugins (kmp-library, koin-compiler, popcorngp) |
