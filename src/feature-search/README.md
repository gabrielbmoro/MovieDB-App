---
module: feature-search
summary: "Debounced movie search with text input, auto-focus, and results list."
keywords: [search, debounce, text-input, autofocus, movies, shared-flow, results]
---

# feature-search

## Purpose
Feature module — provides debounced movie search with inline results. Supports deep-linked queries and auto-focuses the search field on entry.

## Primary Responsibility
- Render the search screen (`SearchScreen`) with a text input and results list
- Debounce user input (600ms) before triggering API searches
- Display results as a scrollable `LazyColumn` of `MovieCard` items

## Existing Functionalities
- **SearchScreen** — `Scaffold` with `CustomAppToolbar` containing `SearchInputText`, auto-focuses keyboard with 500ms delay via `LaunchedEffect`
  - Passes optional `query: String?` parameter (from deep link or navigation), forwarded to ViewModel via Koin `parametersOf`
- **SearchViewModel** — extends `BaseViewModel<SearchUIState, SearchUserIntent, UiEvent>`
  - Intents: `SearchBy(TextFieldValue)`, `ClearSearchField`
  - Debounce via `MutableSharedFlow<String>` + `debounce(600ms)` before calling `repository.searchMovieBy()`
  - Maps `Movie` → `MovieCardInfo` for the UI
- **Model** — `SearchUIState` (searchQuery, results), `SearchUserIntent`
- **Widgets** — `SearchInputText` (TextField with clear X icon), `MoviesResult` (LazyColumn of MovieCard items, also defines a local `MovieCardInfo` data class)
- **DI** — `SearchMovieModule` (`lazyModule`): `viewModel { params -> SearchViewModel(query = params.get(), ...) }`
- **Tests** — `SearchViewModelTest` with `FakeRepository`

## Entry Points

| Entry Point | File | Signature | Role |
|---|---|---|---|
| Screen | `src/commonMain/kotlin/.../ui/screens/search/SearchScreen.kt` | `@Composable fun SearchScreen(query: String?)` | Search screen — Scaffold with search input + results list + auto-focus |
| ViewModel | `src/commonMain/kotlin/.../ui/screens/search/SearchViewModel.kt` | `class SearchViewModel : BaseViewModel<SearchUIState, SearchUserIntent, UiEvent>` | MVI ViewModel — debounced search via MutableSharedFlow |
| Model | `src/commonMain/kotlin/.../ui/screens/search/Model.kt` | `data class SearchUIState`, `sealed interface SearchUserIntent` | MVI state (searchQuery, results) + 2 intents |
| DI | `src/commonMain/kotlin/.../di/SearchMovieModule.kt` | `val featureSearchMovieModule = lazyModule { }` | Koin lazy module — registers SearchViewModel with query param |

## Important Workflows

### Debounced Search Flow
```
User types in SearchInputText
  → TextField onValueChange fires SearchBy(TextFieldValue)
    → ViewModel.executeIntent:
      1. Updates state: searchQuery = new TextFieldValue (immediate, for responsive typing)
      2. Emits searchQuery.text to searchFlow: MutableSharedFlow<String>
    → searchFlow.debounce(600ms) delays emission to avoid API spam
    → collectLatest { query ->
        call repository.searchMovieBy(query)  ⚠ NOT wrapped in runCatching
        → Map List<Movie> → List<MovieCardInfo>
        → Update state: results = mapped list
      }

User clears search field (taps X icon) → fires ClearSearchField intent
  → ViewModel updates state: searchQuery = TextFieldValue(""), results = emptyList()
```

### Deep Link Search Flow
```
Rinku deep link: movie://search?query=batman
  → DeeplinkEffect (in :composeApp) extracts query="batman"
  → Navigates to SearchScreen(query = "batman")
  → SearchScreen passes query to ViewModel via Koin parametersOf(query)
  → ViewModel.init: seedSearchField(query) — sets initial TextFieldValue
  → (No automatic search trigger — user must type to initiate API call)
```

### Keyboard Auto-Focus Sequence
```
SearchScreen composition:
  → val focusRequester = remember { FocusRequester() }
  → LaunchedEffect(Unit) { delay(500); focusRequester.requestFocus() }
  → SearchInputText receives focusRequester → TextField gains focus → keyboard opens
  → 500ms delay allows screen transition animation to complete before focus grab
```

## Critical Files

| File | Role |
|---|---|
| `.../ui/screens/search/SearchScreen.kt` | Screen composable — Scaffold, toolbar search input, conditional results, auto-focus |
| `.../ui/screens/search/SearchViewModel.kt` | ViewModel — debounced search via MutableSharedFlow, query seeding |
| `.../ui/screens/search/Model.kt` | MVI state (`SearchUIState`) + intents (`SearchUserIntent`) |
| `.../ui/widgets/SearchInputText.kt` | Material3 TextField with TextFieldValue + close icon trail |
| `.../ui/widgets/MoviesResult.kt` | LazyColumn of MovieCard items + `MovieCardInfo` data class |
| `.../di/SearchMovieModule.kt` | Koin lazy module — DI wiring for SearchViewModel with query param |

## Internal Dependencies

| Dependency | Relationship |
|---|---|
| `:domain` | `implementation` (MoviesRepository, domain models) |
| `:designsystem` | `implementation` (MovieCard, CustomAppToolbar, BackNavigationIcon, BubbleLoader) |
| `:platform` | `implementation` (BaseViewModel, LoggerHelper, navigation) |

### Key External Libraries
- Compose Multiplatform, kotlinx-collections-immutable, lifecycle-viewmodel-compose, Koin, Navigation Compose

## External Dependencies (Consumers)
- `:composeApp` — aggregates `featureSearchMovieModule` and renders `SearchScreen` at the `Screen.Search?query=` route

## Technical Notes
- The debounce is implemented with `MutableSharedFlow<String>` + `debounce(600)` — this is an in-coroutine debounce, not Compose-side
- Search input uses `TextFieldValue` (not plain `String`) for composition-aware cursor handling
- The keyboard auto-focus uses `FocusRequester` with a `delay(500)` to allow animation to complete

## Technical Debts
- **Wrong package name**: `com.gabrielbmoro.moviedb.search` — should be `com.gabrielbmoro.moviedb.feature.search` per project conventions. All source files and imports are affected.
- **`onFailure` is a no-op**: search failures are silently ignored — no error state, no user feedback. `SearchUIState` has no `errorMessage` field.
- **Duplicate `MovieCardInfo`**: defined locally in `MoviesResult.kt` but also exists in `feature-movies/Model.kt` and `feature-wishlist/MovieList.kt`. Promote to a shared module.
- **`!!` on nullable state**: `SearchScreen` uses `uiState.value.results!!` which crashes if results is null at composition time.
- **Inconsistent DI naming**: `SearchMovieModule` / `featureSearchMovieModule` vs other features' `*Module` / `feature*Module` pattern.
- No empty-state UI when search returns zero results (just an empty list).
- **Missing `runCatching` on API call**: `repository.searchMovieBy(query)` in the ViewModel's `collectLatest` block is not wrapped in `runCatching`. Any API exception kills the coroutine.
- **Missing `LoggerHelper` injection**: The ViewModel does not inject `LoggerHelper`, making it impossible to log errors even if `onFailure` were implemented.
- **Test package naming inconsistency**: Production code uses `com.gabrielbmoro.moviedb.search.*`, but test code uses `com.gabrielbmoro.moviedb.feature.search.*`. These are in different source sets (commonMain vs commonTest) so they compile, but are inconsistent.
- **Only 1 test case** (`ClearSearchField`) — no tests for debounced search, API results mapping, or pre-populated query flow.
