# data

## Purpose
Data layer module — implements repository interfaces defined in `domain`, providing the concrete wiring to TMDB's REST API (via Ktor) and local persistence (via Room). This is the only module that depends on `:domain` and hosts all data-source concerns.

## Primary Responsibility
- Implement `MoviesRepository` interface from `domain`
- Own all DTOs, API response models, Room entities/DAOs, and mapper functions
- Provide platform-specific HTTP clients and database instances via `expect`/`actual`

## Existing Functionalities
- **API client** (`ApiService.kt`) — Ktor-based HTTP client for TMDB endpoints:
  - `GET /movie/{category}?page={n}` — movie listings
  - `GET /movie/{id}` — movie details
  - `GET /movie/{id}/videos` — video streams
  - `GET /search/movie?query={q}` — search
- **Local database** — Room with `FavoriteMovieDTO` entity and `FavoriteMoviesDAO` (CRUD + favorite toggling)
- **Repository implementation** (`MoviesDataRepository.kt`) — delegates to `ApiService` and `FavoriteMoviesDAO`, mapping between DTOs and domain models
- **Mappers** — `ResponseMappers.kt` (API responses → domain), `DataTransferObjMappers.kt` (domain ↔ Room DTOs)
- **BuildKonfig** — reads `MOVIE_DB_API_TOKEN` from `local.properties` / environment, generates `BuildKonfig.API_TOKEN`
- **Platform providers** — `expect`/`actual` for `httpClientEngine()` (OkHttp on Android, Darwin on iOS) and `databaseInstance()` (Room with platform-specific setup)

## Entry Points

| Entry Point | File | Signature | Role |
|---|---|---|---|
| DI module | `src/commonMain/kotlin/.../data/di/DataModule.kt` | `val dataModule = module { }` | Koin module providing `MoviesRepository`, `HttpClient`, `ApiService`, DAO |
| API service | `src/commonMain/kotlin/.../datasources/ktor/ApiService.kt` | `class ApiService(baseUrl, httpClient)` | Ktor HTTP client for TMDB API endpoints |
| Repository impl | `src/commonMain/kotlin/.../repository/MoviesDataRepository.kt` | `internal class MoviesDataRepository(...) : MoviesRepository` | Implements domain repository interface, delegates to API + DAO |
| DB provider | `src/commonMain/kotlin/.../providers/DatabaseProvider.kt` | `expect fun databaseInstance(): AppDatabase` | Platform-specific Room database instantiation |
| HTTP engine | `src/commonMain/kotlin/.../providers/HttpClientEngineProvider.kt` | `expect fun httpClientEngine(): HttpClientEngine` | Platform-specific HTTP engine (OkHttp/Darwin) |

## Important Workflows

### Data Fetch Flow (API → Domain Model)
```
Feature ViewModel calls MoviesRepository.getMoviesFromFilter(filter, page)
  → MoviesDataRepository (internal class in :data)
    → ApiService.getMovies(category, pageNumber)
      → httpClient.get("$baseUrl/movie/$category?page=$pageNumber")
        → Ktor: Bearer auth header, JSON content negotiation
        → Response → check isSuccess()
          → Success: deserialize to PageResponse, extract .results
          → Failure: throw HttpException(statusCode, statusDescription, url, method)
    → ResponseMappers: MovieResponse.toMovie() extension
      → Constructs IMAGE_BASE_URL/w300 for poster, IMAGE_BASE_URL/w780 for backdrop
      → Maps fields: id, title, overview, vote_average, release_date, etc.
    → Returns List<Movie> to feature module
```

### Local Persistence Flow (Favorites)
```
Feature ViewModel calls MoviesRepository.favorite(movie)
  → MoviesDataRepository
    → DataTransferObjMappers: Movie.toFavoriteMovieDTO()
      → Maps Movie.id → FavoriteMovieDTO.movieId (Room auto-generates its own id)
    → FavoriteMoviesDAO.saveFavorite(favoriteMovieDTO)
      → INSERT with OnConflictStrategy.REPLACE

Feature ViewModel calls MoviesRepository.getFavoriteMovies()
  → MoviesDataRepository
    → FavoriteMoviesDAO.allFavoriteMovies() → List<FavoriteMovieDTO>
    → DataTransferObjMappers: FavoriteMovieDTO.toMovie()
      → Maps FavoriteMovieDTO.movieId → Movie.id
      → Sets isFavorite = true (hardcoded — DTO existence = favorited)
```

### Platform Resolution (expect/actual)
```
DataModule requests databaseInstance() at DI time
  → Common: expect fun databaseInstance(): AppDatabase
  → Android actual: Room.databaseBuilder(context, AppDatabase::class.java, dbFileName).build()
  → iOS actual: Room.databaseBuilder with BundledSQLiteDriver, NSFileManager documents dir

DataModule requests httpClientEngine() for Ktor client
  → Common: expect fun httpClientEngine(): HttpClientEngine
  → Android actual: OkHttpEngine(OkHttpConfig())
  → iOS actual: Darwin.create()
```

## Critical Files

| File | Role |
|---|---|
| `.../di/DataModule.kt` | Koin DI wiring — binds `MoviesRepository`, `HttpClient`, `ApiService`, DAO |
| `.../datasources/ktor/ApiService.kt` | Ktor HTTP client with all 4 TMDB endpoint methods |
| `.../repository/MoviesDataRepository.kt` | Repository implementation (internal class) — orchestrates API + Room |
| `.../datasources/ktor/responses/*.kt` (6 files) | `@Serializable` DTOs matching TMDB JSON schema |
| `.../datasources/database/room/FavoriteMoviesDAO.kt` | Room DAO — CRUD operations on favorite_movies table |
| `.../datasources/database/room/dto/FavoriteMovieDTO.kt` | Room `@Entity` — persisted favorite movie record |
| `.../mappers/ResponseMappers.kt` | API JSON responses → domain models (Movie, MovieDetail, VideoStream) |
| `.../mappers/DataTransferObjMappers.kt` | Domain models ↔ Room DTOs (for favorites) |
| `.../providers/DatabaseProvider.kt` | `expect fun databaseInstance()` — platform Room init |
| `.../providers/HttpClientEngineProvider.kt` | `expect fun httpClientEngine()` — platform HTTP engine |

## Internal Dependencies

| Dependency | Relationship |
|---|---|
| `:domain` | `implementation` (repository interfaces, domain models) |

### Key External Libraries
- Ktor (client-core, content-negotiation, kotlinx-json, auth, logging)
- Room (runtime, compiler via KSP)
- SQLite (bundled driver for iOS)
- BuildKonfig (compile-time config generation)
- Koin (core, annotations — DI module)

## External Dependencies (Consumers)
- `:composeApp` — aggregates `dataModule` eagerly via Koin
- No feature module depends on `:data` directly (enforced by Popcorn Guineapig)

## Technical Notes
- **Popcorn Guineapig rule**: `JustWithRule(justWith=["domain"])` — this module can ONLY depend on `:domain`
- `BuildKonfig` is configured in this module's `build.gradle.kts`, so the API token is only accessible to `:data`
- Room KSP is applied per-platform: `kspAndroid`, `kspIosSimulatorArm64`, `kspIosX64`, `kspIosArm64`
- Room schema directory is configured at `$projectDir/schemas` for migration exports
- `MoviesDataRepository` receives `ApiService` and `FavoriteMoviesDAO` via Koin constructor injection
- Image URLs are constructed in mappers using `https://image.tmdb.org/t/p/w300` (thumbnails) and `w780` (large)

## Technical Debts
- Room database version is hardcoded in `DatabaseProvider.kt`; consider version catalog entry
- No repository-level caching strategy — every call hits the network or database directly
- Mapper tests exist but repository implementation tests are missing
- `FavoriteMovieDTO` stores redundant fields (`backdropImageUrl`, `releaseDate`, `language`, `popularity`) that mirror the API response instead of normalizing to domain-only needs
- **`MoviesDataRepository` has no tests** — only the mapper extension functions (`MappersExtTest`) have test coverage. No integration or unit tests verify repository behavior against fake API/DAO.
- **`FavoriteMovieDTO` dual-ID design**: Room auto-generates `id: Int?` while TMDB's ID is stored as `movieId: Long`. This subtle distinction is easy to misuse in mappers.
- **API token scope**: `BuildKonfig.API_TOKEN` is available only within `:data` via `BuildKonfig` generated class, but the token string originates from `local.properties` — an external configuration dependency with no fallback validation.
