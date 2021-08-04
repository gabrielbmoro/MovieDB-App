[![Android Badge](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://www.android.com/)[![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)

# Welcome!

---

## Teaser

![MovieDB teaser](img/teaser.gif)

---

## Architecture and Stack Overview

![Architecture Overview](img/architecture.png)

### Architecture

**Repository**

- This layer provides an interface used as a repository. We also have some entities provided by this repository

- The main idea here is to provide a single abstraction to interact with two different Data Sources.

**Repository->API**

- The APP fetches data from the [Movie DB API](https://www.themoviedb.org). There is a code infrastructure using *Retrofit* where it is possible to keep this communication between the app and the server.

**Repository -> Local Data Base**

- The user can select their favorite movies and store them on a local database. There is a code layer using the *Room* library to keep easy the communication between the app and the Data Base.

**Use Cases**

- This layer is the way used by `ViewModels` to access the repository.

**Use Cases -> Mappers**

- They are used to convert the entities objects from data sources to entities recognized by the domain layer.

**Presentation**

- Contains `ViewModels`, `Fragments`, `Activities`, and others `Compose` functions.

**Core**

- The core layer starts the Dependency injection engine, the network setup, and the database configuration.

---

### Tech Stack Summary

- Compose, Dagger Hilt, Coroutines, Retrofit, Room, Mockk.

- More information about the Android Jetpack libraries used here, please access the [MAD Score card](https://madscorecard.withgoogle.com/scorecard/share/3723779503)
