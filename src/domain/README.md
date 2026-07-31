# domain

## Purpose
Core business logic module — defines the ubiquitous domain language, repository contracts, and use cases. This is a LEAF module with zero project module dependencies, following Clean Architecture principles.

## Primary Responsibility
- Define domain models that the entire app agrees on (`Movie`, `MovieDetail`, `VideoStream`, `HttpException`, `MovieListType`)
- Declare `MoviesRepository` interface — the contract that `:data` implements
- Implement business-logic use cases that orchestrate repository calls

## Existing Functionalities
- **Domain Models**
  - `Movie` — core movie representation (id, title, rating, poster, overview, etc.)
  - `MovieDetail` — full movie detail including genres, budget, homepage, production companies
  - `MovieListType` — enum: `TOP_RATED`, `FAVORITE`, `POPULAR`, `UPCOMING` with `Int.convertToMovieListType()` extension
  - `VideoStream` — YouTube video metadata (id, key, site, type)
  - `HttpException` — custom exception with `statusCode`, `statusDescription`, `requestUrl`, `requestMethod`
- **Repository Interface** (`MoviesRepository`) — 7 methods:
  - `getMoviesFromFilter(filter, page)`, `getMovieDetail(movieId)`, `getVideoStreams(movieId)`, `searchMovieBy(query)`
  - `getFavoriteMovies()`, `favorite(movie)`, `unFavorite(movieTitle)`, `checkIsAFavoriteMovie(movieTitle)`
- **Use Cases**
  - `FavoriteMovieUseCase` — toggle movie favorite status (wraps `repository.favorite()` / `repository.unFavorite()`)
  - `GetMovieDetailsUseCase` — fetch movie details + video streams, filter for official YouTube trailers, attach `videoId` to `MovieDetail`
  - `UseCase<Input, Output>` — generic interface with `suspend fun execute(input: Input): Output`
- **DI** — `DomainModule` (`@ComponentScan("com.gabrielbmoro.moviedb.domain")`) uses Koin Annotations auto-discovery for `@Factory`-annotated use case implementations
- **Tests** — use case tests with hand-written `FakeRepository`, model type tests

## Entry Points

| Entry Point | File | Signature | Role |
|---|---|---|---|
| Repository contract | `src/commonMain/kotlin/.../domain/MoviesRepository.kt` | `interface MoviesRepository` (7 suspend methods) | Contract boundary between domain and data layers |
| Use case base | `src/commonMain/kotlin/.../usecases/UseCase.kt` | `interface UseCase<Input, Output>` | Generic use case contract with single `execute()` method |
| Favorite use case | `src/commonMain/kotlin/.../usecases/FavoriteMovieUseCase.kt` | `interface FavoriteMovieUseCase : UseCase<Params, Unit>` | Toggle movie favorite status |
| Details use case | `src/commonMain/kotlin/.../usecases/GetMovieDetailsUseCase.kt` | `interface GetMovieDetailsUseCase : UseCase<Params, MovieDetail>` | Fetch details + trailer enrichment |
| Domain models | `src/commonMain/kotlin/.../domain/model/*.kt` (5 files) | `Movie`, `MovieDetail`, `VideoStream`, `HttpException`, `MovieListType` | Ubiquitous language types used across all layers |
| DI module | `src/commonMain/kotlin/.../domain/di/DomainModule.kt` | `@ComponentScan @Module class DomainModule` | Koin Annotations auto-discovery of use case implementations |

## Important Workflows

### Use Case Execution Flow
```
Feature ViewModel instantiates a use case (e.g., GetMovieDetailsUseCase)
  → Calls useCase.execute(Params(movieId))
    → GetMovieDetailsUseCaseImpl (internal class in :domain)
      → 1. repository.getMovieDetail(movieId) → MovieDetail
      → 2. repository.getVideoStreams(movieId) → List<VideoStream>
      → 3. Filter: first matching (site=="YouTube" && official && type=="Trailer")
      → 4. Return movieDetail.copy(videoId = videoStream?.key)
```

### Favorite Toggle Workflow
```
Feature ViewModel calls FavoriteMovieUseCase
  → FavoriteMovieUseCaseImpl.execute(Params(...))
    → Constructs Movie from Params fields (with isFavorite = true)
    → If toFavorite == true: repository.favorite(movie)
    → If toFavorite == false: repository.unFavorite(movieTitle)
      ⚠ Note: unfavorite uses movieTitle as unique key, not movieId
```

### Data Layer Contract (Dependency Inversion)
```
:domain defines interface MoviesRepository (7 suspend methods)
  → :data module implements MoviesDataRepository : MoviesRepository (internal class)
  → Feature modules depend on MoviesRepository interface (never on :data)
  → Koin DI wires MoviesDataRepository for MoviesRepository at runtime
```

## Critical Files

| File | Role |
|---|---|
| `.../domain/MoviesRepository.kt` | Repository interface — the domain/data boundary (Dependency Inversion) |
| `.../usecases/UseCase.kt` | Base use case contract: `suspend fun execute(input: Input): Output` |
| `.../usecases/FavoriteMovieUseCase.kt` | Favorite/unfavorite logic + `FavoriteMovieUseCaseImpl` (internal) |
| `.../usecases/GetMovieDetailsUseCase.kt` | Detail fetch + YouTube trailer filter + `GetMovieDetailsUseCaseImpl` (internal) |
| `.../model/Movie.kt` | Core movie domain model (10 fields) + 3 mock factory methods |
| `.../model/MovieDetail.kt` | Extended movie detail (17 fields) with mutable `videoId` var |
| `.../model/VideoStream.kt` | YouTube video metadata (id, key, site, type, official) |
| `.../model/HttpException.kt` | Structured HTTP error model (status code, description, URL, method) |
| `.../model/MovieListType.kt` | Filter category enum (TOP_RATED, FAVORITE, POPULAR, UPCOMING) + Int converter |
| `.../di/DomainModule.kt` | Koin `@ComponentScan` module — auto-discovers use case implementations |

## Internal Dependencies
- **None** — LEAF module, no project module dependencies (enforced by Popcorn Guineapig `NoDependencyRule`)

### Key External Libraries
- Koin (annotations, core — for DI module compilation)
- kotlinx-coroutines-core
- kotlinx-serialization (implicitly through KMP stdlib)

## External Dependencies (Consumers)
- `:data` — implements `MoviesRepository` interface
- `:feature-movies`, `:feature-search`, `:feature-wishlist` — all depend on domain models and use cases
- `:composeApp` — aggregates `DomainModule` via Koin

## Technical Notes
- **Popcorn Guineapig rule**: `NoDependencyRule` — must have NO project module dependencies
- Use cases follow the `UseCase<Input, Output>` pattern with a single `suspend fun execute(input)`
- Koin Annotations with `@Module` + `@ComponentScan` auto-discovers `@Factory`-annotated implementations
- `MovieListType` enum maps to TMDB API category strings via `MoviesHandler` in `feature-movies`
- Tests use hand-written fakes (`FakeRepository`) per the project's no-mocking-frameworks standard

## Technical Debts
- `Movie` contains three `companion object` mock factory methods used only in tests — should live in test sources
- `FavoriteMovieUseCase.Params` duplicates all `Movie` fields — consider using the domain model directly with `copy()`
- `HttpException` extends `IllegalStateException` — should extend a more appropriate base or be replaced with a sealed result type
- Only two use cases tested; the generic `UseCase` interface has no tests
- **Favorites keyed by title, not ID**: `MoviesRepository.unFavorite(movieTitle)` and `checkIsAFavoriteMovie(movieTitle)` use `movieTitle: String` as the lookup key. Movies with identical titles (e.g., remakes) collide. `Movie.id: Long` is the proper unique key.
- **`MovieDetail` duplicates `Movie` fields**: `MovieDetail` re-declares `votesAverage`, `title`, `posterImageUrl`, `backdropImageUrl`, `overview`, `releaseDate`, `language`, `popularity` instead of composing from a `Movie` property. Changes to `Movie` must be mirrored manually in `MovieDetail`.
- **`FavoriteMovieUseCaseImplTest` tests only the unfavorite path** — no test for `toFavorite = true` (the addition path).
