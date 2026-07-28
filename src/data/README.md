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
