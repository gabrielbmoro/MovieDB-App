<a href="https://devlibrary.withgoogle.com/products/android/repos/gabrielbmoro-MovieDB-Android">
    <img src="img/googleDevLibraryLogo.png" alt="Google Dev Library Logo" style="width:300px;"/>
</a>

[![Android Badge](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://www.android.com/)[![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)

[![Build Status](https://app.bitrise.io/app/4aa44eea-43cf-4a4d-8996-5ed6f48d9512/status.svg?token=C6RzgrGuhGeDARNPMAqxuw&branch=main)](https://app.bitrise.io/app/4aa44eea-43cf-4a4d-8996-5ed6f48d9512)

# Welcome! 👋

Welcome to the MovieDB-Android app! If you like movies, you will like this app, because you can access the most popular and top rated movies 🤩

---

## How to run the project? 📦

1. Install Android Studio (latest version);
2. Select the option to open a project;
3. Select the project -> ⚠️ The Android project is inside of the `src` directory ⚠️ 

---

## How to get my access token? 👮 

1. Create an account [Movie DB API](https://www.themoviedb.org) (if you don't have it);
2. Copy the value of your [Bearer token](https://developer.themoviedb.org/docs/authentication-application#bearer-token);
3. Paste the value of your token your `gradle.properties` file (_Global properties_):

If your bearer token is:
```
Bearer cHuckNoRRisIsAWarRior
```

Your `gradle.properties` file (_Global properties_) should looks like:

```
MOVIE_DB_API_TOKEN_DEBUG=cHuckNoRRisIsAWarRior
MOVIE_DB_API_TOKEN_RELEASE=cHuckNoRRisIsAWarRior
```

---

## Teaser 🎬

<img src="img/teaser.gif" height="500" />

---

### Tech stack summary 🛠️

- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform)
- [Voyager](https://voyager.adriel.cafe)
- [Koin](https://github.com/InsertKoinIO/koin)
- Coroutines
- [Ktor](https://ktor.io)
- [Moko Resources](https://github.com/icerockdev/moko-resources)
- Room
- Gradle Catalogs
- [Android YouTube Player](https://github.com/PierfrancescoSoffritti/android-youtube-player)

---

## Wiki 📚

More details about the project you can visit the [Wiki](https://github.com/gabrielbmoro/MovieDB-Android/wiki). 
