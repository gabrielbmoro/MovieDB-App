<a href="https://devlibrary.withgoogle.com/products/android/repos/gabrielbmoro-MovieDB-Android">
    <img src="img/googleDevLibraryLogo.png" alt="Google Dev Library Logo" style="width:300px;"/>
</a>

[![Build Status](https://app.bitrise.io/app/4aa44eea-43cf-4a4d-8996-5ed6f48d9512/status.svg?token=C6RzgrGuhGeDARNPMAqxuw&branch=main)](https://app.bitrise.io/app/4aa44eea-43cf-4a4d-8996-5ed6f48d9512)
[![Kotlin](https://img.shields.io/badge/kotlin-2.1.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-iosX64](https://img.shields.io/badge/platform-iosX64-CDCDCD?style=flat)
![badge-iosArm64](https://img.shields.io/badge/platform-iosArm64-CDCDCD?style=flat)
![badge-iosSimulatorArm64](https://img.shields.io/badge/platform-iosSimulatorArm64-CDCDCD?style=flat)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/gabrielbmoro/MovieDB-Android/issues)

# Welcome! 👋

Welcome to the MovieDB-App! If you like movies, you will like this app, because you can access the most popular and top rated movies 🤩

More details about the project you can take a look in our [Wiki](https://github.com/gabrielbmoro/MovieDB-App/wiki) 📚

---

## How to run the project 📦

1. Install Android Studio (latest version);
2. Select the option to open a project;
3. Select the project -> ⚠️ The project is inside of the `src` directory ⚠️

🍎 If you wants to run the iOS app, you should open the file `iosApp/iosApp.xcodeproj` using Xcode (latest version).

🤖 Otherwise, you can run the Android app opening the src file using the Android Studio.

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
MOVIE_DB_API_TOKEN=cHuckNoRRisIsAWarRior
```

---

## Teaser 🎬

- 🤖 **Android**

You can see the Android teaser [here](/img/android-teaser.webm)

- 🍎 **iOS**

![iOS teaser](/img/ios-teaser.gif)

---

### Tech stack summary 🛠️

- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform)
- [Koin](https://github.com/InsertKoinIO/koin) - Dependency injection
- [Rinku](https://github.com/theolm/Rinku) - Deep links
- Coroutines
- [Ktor](https://ktor.io) - Network requests
- [Coil](https://coil-kt.github.io/coil/compose) - Image loader
- [Room](https://developer.android.com/kotlin/multiplatform/room) - Multiplatform database
- Gradle Catalogs - Dependency management
- [Android YouTube Player](https://github.com/PierfrancescoSoffritti/android-youtube-player) - Android video player
- [BuildKonfig](https://github.com/yshrsmz/BuildKonfig) - Gradle variables
- [Popcorn Guineapig](https://github.com/CodandoTV/popcorn-guineapig) - Module analysis
- [Kermit](https://kermit.touchlab.co/) - Logging