# Welcome!

Android native App where the user can list and favorite some Movies.

## Architecture and Stack Overview

![Architecture Overview](img/architecture.png)

### Repository

This layer provides an interface used as a repository. We also have some entities provided by this repository. The main idea here is to provide a single abstraction to interact with two different Data Sources.

#### API

The APP fetches data from the [Movie DB API](https://www.themoviedb.org). There is a code infrastructure using **Retrofit** where it is possible to keep this communication between the app and the server.

#### Local Data Base

The user can select their favorite movies and store them on a local database. There is a code layer using the **Room** library to keep easy the communication between the app and the Data Base.

### Use Cases

#### Mappers

<Describe here something>

### Presentation

### Core

---

## Libraries
