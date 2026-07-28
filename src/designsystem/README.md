# designsystem

## Purpose
Shared UI module — provides the design tokens (colors, typography), reusable composable components, and the Material 3 theme wrapper consumed by all feature modules. A LEAF module with no project dependencies.

## Primary Responsibility
- Define the `MovieDBAppTheme` composable (dark/light color schemes, Material 3 integration)
- Provide reusable UI composables: cards, toolbars, image loaders, error screens, loaders, icons, buttons
- Host compose resources (strings, drawables) shared across feature modules

## Existing Functionalities
- **Theme** — `Color.kt` (amber/gold primary color schemes: `movieDBDarkColorScheme`, `movieDBLightColorScheme`), `Theme.kt` (`MovieDBAppTheme` composable with optional dynamic color support)
- **Cards** — `MovieCard` (poster + info, optional delete button), `MovieCardInformation` (title, star rating, description)
- **Toolbars** — `AppToolbarTitle` (standard top bar), `CustomAppToolbar` (slot-based top bar), `AnimatedAppToolbar` (visibility animation wrapper), `NavigationBottomBar` (Movies | Favorite tabs)
- **Images** — `AsyncImage` (Coil 3 wrapper with progress/error states), `MovieImage` (image or placeholder), `EmptyState` (sad emoji + text), `FiveStars` (0-5 star rating display from vote average)
- **Error** — `ErrorInfo` (enum: `SOMETHING_WRONG_HAPPENED`, `NETWORK_ERROR`, `PLEASE_TRY_AGAIN`), `ErrorScreen` (composable with retry action)
- **Loaders** — `BubbleLoader` (animated three-bubble canvas loader)
- **Icons** — `BackNavigationIcon`, `SearchNavigationIcon` (Material icon buttons)
- **Buttons** — `DeleteButton` (trash icon button)
- **Resources** — strings.xml (localized labels), drawable XML icons (star variants, movie media player, navigation, sad emoji)

## Entry Points

| Entry Point | File | Signature | Role |
|---|---|---|---|
| Theme wrapper | `src/commonMain/kotlin/.../theme/Theme.kt` | `@Composable fun MovieDBAppTheme(dynamicColorScheme: ColorScheme?, content: @Composable () -> Unit)` | Material 3 theme with optional dynamic color injection |
| Color schemes | `src/commonMain/kotlin/.../theme/Color.kt` | `val movieDBDarkColorScheme: ColorScheme`, `val movieDBLightColorScheme: ColorScheme` | Amber/gold custom color schemes (30+ tokens) |
| Movie card | `src/commonMain/kotlin/.../cards/MovieCard.kt` | `@Composable fun MovieCard(imageUrl, title, description, votes, onClick, enableDelete, onDeleteClick)` | Primary movie card used across all feature screens |
| Image loader | `src/commonMain/kotlin/.../images/MovieImage.kt` | `@Composable fun MovieImage(imageUrl, contentDescription, contentScale)` | Public image composable — delegates to internal `AsyncImage` with placeholder fallback |
| Five stars | `src/commonMain/kotlin/.../images/FiveStars.kt` | `@Composable fun FiveStars(votes: Float)` | TMDB 0-10 vote → 5-star display with half-star support |
| Error screen | `src/commonMain/kotlin/.../error/ErrorScreen.kt` | `@Composable fun ErrorScreen(errorInfo: ErrorInfo, onRetry: (() -> Unit)?)` | Full-screen error with optional retry button |
| Toolbars | `src/commonMain/kotlin/.../toolbars/*.kt` (4 files) | `AppToolbarTitle`, `CustomAppToolbar`, `AnimatedAppToolbar`, `NavigationBottomBar` | Standard top bars and 2-tab bottom navigation |
| Internal image | `src/commonMain/kotlin/.../images/AsyncImage.kt` | `internal fun AsyncImage(...)` | Coil 3 wrapper with loading/error states (not exported) |

## Important Workflows

### Theme Resolution Flow
```
MovieDBAppTheme(dynamicColorScheme = platformProvidedScheme)
  → If dynamicColorScheme != null: MaterialTheme(colorScheme = dynamicColorScheme)
    → Android: MainActivity.DynamicColorApp generates dynamicDarkColorScheme/LightColorScheme (API 31+)
    → iOS: always null → uses fallback
  → If dynamicColorScheme == null: MaterialTheme(colorScheme = if (isSystemInDarkTheme()) movieDBDarkColorScheme else movieDBLightColorScheme)
  → No custom Typography or Shapes — uses Material3 defaults
```

### Image Loading Flow
```
MovieImage(imageUrl = "https://image.tmdb.org/t/p/w300/abc.jpg")
  → If imageUrl == null: show ic_movie_media_player placeholder
  → If imageUrl != null: delegate to internal AsyncImage
    → AsyncImage builds Coil ImageRequest with KtorNetworkFetcherFactory
    → SubcomposeAsyncImage renders:
      - Loading state: CircularProgressIndicator (52dp max)
      - Success state: loaded image with contentScale
      - Error state: custom onFailure composable (passed from MovieImage)
```

### Movie Card Composition Flow
```
MovieCard(imageUrl, title, description, votes, onClick, enableDelete, onDeleteClick)
  → Card (rounded 12dp corners, elevation)
    → MovieImage (200dp height, FillWidth scale) — poster or placeholder
    → MovieCardInformation (title bold, FiveStars, description italic)
    → If enableDelete: DeleteButton overlay (48dp, positioned with BoxScope.align)
```

## Critical Files

| File | Role |
|---|---|
| `.../theme/Color.kt` | 30+ color constants + `movieDBDarkColorScheme` / `movieDBLightColorScheme` |
| `.../theme/Theme.kt` | `MovieDBAppTheme` — Material 3 wrapper with dynamic color support |
| `.../cards/MovieCard.kt` | Primary card — image + info + optional delete button |
| `.../images/AsyncImage.kt` | Internal Coil 3 wrapper — loading/error/empty states (not exported) |
| `.../images/MovieImage.kt` | Public image — null-safe with ic_movie_media_player placeholder |
| `.../images/FiveStars.kt` | 0-10 vote average → 5 star icons (star, half-star, border) |
| `.../error/ErrorScreen.kt` | Error state composable — icon + message + optional retry button |
| `.../error/ErrorInfo.kt` | Error discriminator enum: SOMETHING_WRONG_HAPPENED, NETWORK_ERROR, PLEASE_TRY_AGAIN |
| `.../toolbars/AppToolbarTitle.kt` | Standard top bar with back + search icon buttons |
| `.../toolbars/CustomAppToolbar.kt` | Slot-based top bar (composable title, not string) |
| `.../toolbars/AnimatedAppToolbar.kt` | Wraps any toolbar in expand/shrink animation (200ms delay, 500ms duration) |
| `.../toolbars/NavigationBottomBar.kt` | 2-tab bar: Movies (index 0) + Favorite (index 1) |
| `.../loaders/BubbleLoader.kt` | Custom 3-bubble Canvas animation loader |
| `.../icons/BackNavigationIcon.kt` | Auto-mirrored back arrow (RTL support) |
| `.../icons/SearchNavigationIcon.kt` | Search icon button |
| `.../buttons/DeleteButton.kt` | Trash icon button |
| `.../images/EmptyState.kt` | Sad emoji + "No movie here" centered column |

## Internal Dependencies
- **None** — LEAF module, no project module dependencies (enforced by Popcorn Guineapig `NoDependencyRule`)

### Key External Libraries
- Compose Multiplatform (ui, foundation, material3, runtime, animation, ui-util, material-icons-extended)
- Coil 3 (compose + ktor3 network fetcher)

## External Dependencies (Consumers)
- `:composeApp` — declared as `api` dependency, so all consumers transitively inherit theme/components
- `:feature-movies`, `:feature-details`, `:feature-search`, `:feature-wishlist` — use cards, toolbars, images, loaders, error components

## Technical Notes
- **Popcorn Guineapig rule**: `NoDependencyRule` — must have NO project module dependencies
- `AsyncImage` uses Coil 3 with `KtorNetworkFetcherFactory` for consistent networking across platforms
- `FiveStars` maps TMDB's 0-10 vote average to 0-5 stars with half-star support
- Material 3 `ColorScheme` definitions include all 30+ color tokens for complete theme support
- `NavigationBottomBar` is a two-tab bar (Movies index 0, Wishlist index 1) — not a general-purpose navigation component
- String resources are localized via `composeResources/values/strings.xml`

## Technical Debts
- **Typo in package path**: `com/gabrielbmoro/moviedb/desingsystem/` — missing `i` in "designsystem" (`deSignsystem` should be `deSignSystem`). The package name matches the directory, so changing either requires updating all imports across the app.
- `FiveStars` uses hardcoded star icon resources — doesn't support custom icon sets
- `BubbleLoader` uses raw `Canvas` drawing instead of Material 3 `CircularProgressIndicator`
- `NavigationBottomBar` is fixed to two tabs — not extensible without code changes
- No unit tests exist for any composable (no Compose UI testing setup)
- **`CustomAppToolbar.kt` missing package declaration**: file starts directly with `import` statements — no `package com.gabrielbmoro.moviedb.desingsystem.toolbars` line. It compiles because the directory path implies the package, but Detekt should flag this.
- **No custom `Typography` or `Shapes`**: `MovieDBAppTheme` sets only `colorScheme` on `MaterialTheme` — typography and shapes use Material3 defaults, which limits brand customization.
