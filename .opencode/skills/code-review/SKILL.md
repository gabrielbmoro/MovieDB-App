---
name: code-review
description: Review Kotlin Multiplatform + Compose code following Clean Architecture, MVI pattern, Detekt rules, and project conventions
license: MIT
compatibility: opencode
metadata:
  audience: developers
  workflow: pr-review
---

## What I do

When asked to review code, I first compare the current branch to `main` using `git diff main...HEAD` to see all changes, then check that changes follow MovieDB-App's established conventions:

### Architecture (Clean Architecture)
- Domain layer (no project dependencies) — entities, repository interfaces, use cases
- Data layer — depends only on domain; implements repository interfaces
- Feature modules — depends on domain but NOT on data (enforced by Popcorn Guineapig)
- No feature module should import anything from `data` layer

### MVI Pattern
Each feature should have these files:
- **`Model.kt`** — sealed interface for user intents, data class for UI state
- **`*ViewModel.kt`** — extends `ViewModel` + `ViewModelMvi<UserIntent>`; exposes state via `StateFlow` using `stateIn(viewModelScope, SharingStarted.Eagerly, ...)`
- **`*Screen.kt`** — Composable that collects state via `collectAsState()` and dispatches intents via `viewModel.execute(intent)`

### Detekt Conventions
- Max line length: 120
- Cyclomatic complexity: ≤ 15
- Method length: ≤ 60 lines
- Max return count: ≤ 2
- Naming: camelCase for functions/variables, PascalCase for classes
- Trailing commas required at call and declaration sites

### Testing Standards
- Framework: `kotlin.test` (`@Test`, `@BeforeTest`, `@AfterTest`)
- Coroutines: `kotlinx-coroutines-test` (`StandardTestDispatcher`, `runTest`, `advanceUntilIdle`)
- No mocking frameworks — use hand-written fakes (`FakeRepository`, `FakeUseCase`)
- Tests located in `src/commonTest/` per module

### Dependency Injection (Koin Annotations)
- `@Module`, `@Factory`, `@Single`, `@Provided` annotations
- All dependency versions from `src/gradle/libs.versions.toml` (version catalog)
- Feature modules loaded lazily via `lazyModules()`

### State Management
- `StateFlow` in ViewModels
- UI collects via `collectAsState()`
- Immutable collections (`kotlinx-collections-immutable`) for UI state data

### Review Checklist
- [ ] Architecture dependency rules respected (no data leaks into feature)
- [ ] MVI pattern followed (Model.kt, ViewModel, Screen separation)
- [ ] Detekt rules satisfied (line length, complexity, naming, trailing commas)
- [ ] Tests use kotlin.test with fakes, not mock libraries
- [ ] Tests in correct `commonTest/` location
- [ ] DI uses Koin Annotations, versions from catalog
- [ ] State uses StateFlow + collectAsState with immutable collections
- [ ] Compose resources via `Res.string.*` for strings
