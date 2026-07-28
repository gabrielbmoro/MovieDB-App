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

## Internal Dependencies
- **None** — LEAF module, no project module dependencies (enforced by Popcorn Guineapig `NoDependencyRule`)

### Key External Libraries
- Koin (annotations, core — for DI module compilation)
- kotlinx-coroutines-core
- kotlinx-serialization (implicitly through KMP stdlib)

## External Dependencies (Consumers)
- `:data` — implements `MoviesRepository` interface
- `:feature-movies`, `:feature-details`, `:feature-search`, `:feature-wishlist` — all depend on domain models and use cases
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
