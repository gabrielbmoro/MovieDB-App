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
