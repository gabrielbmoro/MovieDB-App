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
