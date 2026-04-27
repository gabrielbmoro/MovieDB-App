# MovieDB 🎬

[![Build Status](https://app.bitrise.io/app/4aa44eea-43cf-4a4d-8996-5ed6f48d9512/status.svg?token=C6RzgrGuhGeDARNPMAqxuw&branch=main)](https://app.bitrise.io/app/4aa44eea-43cf-4a4d-8996-5ed6f48d9512)
[![Kotlin](https://img.shields.io/badge/kotlin-2.3.20-blue.svg?logo=kotlin)](http://kotlinlang.org)
![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](https://img.shields.io/badge/platform-ios-CDCDCD?style=flat)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/gabrielbmoro/MovieDB-Android/issues)
[![License: MIT](https://img.shields.io/badge/license-MIT-yellow.svg)](license)

A **Kotlin Multiplatform** app built with **Compose Multiplatform** that lets you browse, search, and discover movies using [The Movie Database (TMDB) API](https://www.themoviedb.org). Runs on **Android** and **iOS** from a single shared codebase.

<a href="https://devlibrary.withgoogle.com/products/android/repos/gabrielbmoro-MovieDB-Android">
  <img src="img/googleDevLibraryLogo.png" alt="Google Dev Library" width="300" />
</a>

---

## Features ✨

| Feature | Description |
|---|---|
| **Browse Movies** | Paginated grid of popular, top-rated, upcoming, and now-playing movies |
| **Movie Details** | Backdrop image, rating, release date, genres, overview, and trailers |
| **Search** | Debounced search with inline results as you type |
| **Wishlist** | Add/remove favorites persisted locally with swipe-to-delete |
| **Deep Links** | Open movies, search, and favorites from external links (Rinku) |
| **Theming** | Dynamic Material 3 with dark/light mode support |
| **Cross-Platform** | Shared UI and business logic for Android & iOS |

---

## Teaser 🎬

<details open>
<summary>Android</summary>

🎥 [View Android teaser video](img/android-teaser.webm)
</details>

<details open>
<summary>iOS</summary>

![iOS teaser](img/ios-teaser.gif)
</details>

---

## Tech Stack 🛠️

| Category | Libraries |
|---|---|
| **UI** | Compose Multiplatform 1.10.3, Material 3, Navigation Compose |
| **Networking** | Ktor 3.4.2 (OkHttp / Darwin engines), kotlinx-serialization |
| **Image Loading** | Coil 3 (ktor3 network engine) |
| **Database** | Room 2.8.4 with sqlite-bundled |
| **Dependency Injection** | Koin 4.2.1 (annotations + KSP) |
| **State Management** | Coroutines, StateFlow, MVI pattern |
| **Architecture** | Clean Architecture (data/domain/feature layers) |
| **Logging** | Kermit 2.1.0 |
| **Deep Links** | Rinku 1.6.0 |
| **Build Config** | BuildKonfig 0.18.0 |
| **CI** | Bitrise + GitHub Actions |
| **Crash Reporting** | Firebase Crashlytics |
| **Analytics / Performance** | Kotzilla 2.0.8 |
| **Linting** | Detekt 1.23.8 |
| **Coverage** | Kover 0.9.8 |
| **Dependency Audit** | Popcorn Guineapig 3.1.6 |

---

## Architecture 🏗️

### Clean Architecture + MVI

The app follows **Clean Architecture** with strict module dependency rules enforced by Popcorn Guineapig:

```
composeApp (UI orchestrator)
  │
  ├── feature:*      → depends on domain, designsystem, platform
  ├── domain         → LEAF — pure business logic, no project dependencies
  ├── data           → depends ONLY on domain (implements repository interfaces)
  ├── designsystem   → depends only on util:media
  ├── platform       → navigation, paging, MVI base
  └── util:*         → LEAF — no project dependencies
```

**Data flow:**

```
UI (Screen composable)
  → ViewModel (intent → state via StateFlow)
    → UseCase (business logic)
      → Repository interface (domain layer)
        → MoviesDataRepository (data layer)
          → ApiService (Ktor → TMDB API)
          → FavoriteMoviesDAO (Room → SQLite)
```

### Navigation

| Route | Screen |
|---|---|
| `/movies` | Main movie grid with category filter tabs |
| `/details/{movieId}` | Movie detail page |
| `/search` | Search screen |
| `/wishlist` | Favorites list |

Deep links via Rinku: `movie/{id}`, `search?query=`, `favorite`

---

## Project Structure 📁

```
src/
├── composeApp/              # NavHost, DI aggregator, RootApp
├── data/                    # ApiService, DTOs, DAOs, DatabaseProvider
├── domain/                  # Entities, Repository interfaces, UseCases, Mappers
├── designsystem/            # Theme, Colors, shared UI components
├── platform/                # Navigation (Screen enum), PagingController, MVI base
├── feature/
│   ├── feature-movies/      # Movie grid with filter tabs + pagination
│   ├── feature-details/     # Movie detail (backdrop, rating, favorite, info)
│   ├── feature-search/      # Debounced search with results
│   └── feature-wishlist/    # Favorites list with swipe-to-delete
├── util/
│   ├── media/               # AsyncImage (Coil), VideoPlayer (expect/actual)
│   └── logging/             # Kermit wrapper
├── androidApp/              # Android entry point (Application, MainActivity)
├── iosApp/                  # Xcode project
└── build-logic/             # Convention plugins (KMP, Koin KSP, Popcorn GP)
```

---

## Getting Started 🚀

### Prerequisites

- **Android Studio** (latest stable)
- **Xcode** (latest stable) — for iOS
- **JDK 21**
- **A TMDB API key** (see below)

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/gabrielbmoro/MovieDB-App.git
   ```

2. **Open in Android Studio**
   - File → Open → select the `src/` directory

3. **Get a TMDB API token**
   - Create an account at [themoviedb.org](https://www.themoviedb.org)
   - Copy your [Bearer token](https://developer.themoviedb.org/docs/authentication-application#bearer-token)
   - Add it to `src/local.properties`:
     ```properties
     MOVIE_DB_API_TOKEN=your_bearer_token_here
     ```

4. **Run the app**

   | Platform | Instructions |
   |---|---|
   | **Android** | Select the `androidApp` run configuration in Android Studio and hit Run |
   | **iOS** | Open `iosApp/iosApp.xcodeproj` in Xcode, build, and run on a simulator |

---

## Contributing 🤝

Contributions are welcome! Please check the [Contributor Covenant Code of Conduct](CODE_OF_CONDUCT.md) and our [contribution guidelines](.github/pull_request_template.md).

- **Report issues**: [GitHub Issues](https://github.com/gabrielbmoro/MovieDB-Android/issues)
- **Submit PRs**: Open a pull request with a clear description of your changes
- **Wiki**: See the [project Wiki](https://github.com/gabrielbmoro/MovieDB-App/wiki) for more documentation

---

## License 📄

This project is licensed under the MIT License — see the [license](license) file for details.
