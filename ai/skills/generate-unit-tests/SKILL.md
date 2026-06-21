---
name: generate-unit-tests
description: Generate unit tests for changed code following kotlin.test and hand-written fakes conventions.
trigger: when the user asks to write tests, review test files, or generate new test cases
---

# Generate Unit Tests

When invoked:

## Steps

### 1. Identify changed files
- Run `git diff main...HEAD --name-only -- '*.kt'` to list changed Kotlin source files
- Exclude test files (`commonTest/`, `test/`, `androidTest/`) from the diff analysis
- Exclude build-logic and generated files

### 2. For each changed file, determine the test target
- If the file is in `domain/` → test the UseCase / Repository interface logic
- If the file is in `data/` → test the Repository implementation (with fakes for API/DAO)
- If the file is in a `feature-*/` module → test the ViewModel (with fakes for UseCases)
- If the file is in `platform/` → test the utility class
- If the file is in `designsystem/` → no unit tests needed (UI-only)

### 3. Create the test file
- Mirror source path under `src/<module>/commonTest/`
- Use `kotlin.test` framework: `@Test`, `@BeforeTest`, `@AfterTest`
- Use `kotlinx-coroutines-test`: `StandardTestDispatcher`, `runTest`, `advanceUntilIdle`
- Use hand-written fakes, never mocking frameworks
- Follow existing test patterns in the project

### 4. Test conventions to follow
- Test class name: `<Target>Test` (e.g., `PopularMoviesUseCaseTest`)
- Fake class name: `Fake<Target>` (e.g., `FakeMoviesRepository`)
- Import test dependencies from the project bundles: `test` or `test_multiplatform` from `src/gradle/libs.versions.toml`
- Verify each test compiles by running `./gradlew composeApp:compileKotlinDesktop` from `src/`

## When NOT to use

- If the diff is only in `designsystem/`, `iosApp/`, `androidApp/`, or `build-logic/`
- If the diff only contains test files (no source changes to test)
- If the diff is configuration-only (`.toml`, `.gradle.kts`, `.yml`, etc.)
