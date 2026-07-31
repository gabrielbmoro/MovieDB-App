# feature-tvshowdetails

## Purpose
Feature module — displays full TV show details including backdrop, rating, genres, TV-specific info (seasons, episodes, networks, created by, status), and video trailers. Accessed via card tap from the TV shows grid with a `tvShowId` argument.

## Primary Responsibility
- Render the TV show detail screen (`TvShowDetailsScreen`) with a vertically scrollable layout
- Fetch TV show details + video streams via `GetTvShowDetailsUseCase` + `TvShowsRepository`

## Existing Functionalities
- **TvShowDetailsScreen** — three rendering states: loading (`BubbleLoader`), error (`ErrorMessage`), content (scrollable `Column`)
- **TvShowDetailsViewModel** — extends `BaseViewModel<TvShowDetailsUIState, TvShowDetailsUserIntent, UiEvent>`
  - Intents: `LoadTvShowDetails(tvShowId)`, `HideVideo`
  - Uses `runCatching` with proper error handling (no silent swallowing)

## Dependencies
- `:domain` — TvShowsRepository, GetTvShowDetailsUseCase, TvShowDetail, VideoStream
- `:designsystem` — shared widgets (GenresCard, SectionTitle, SectionBody, TextUrl, ErrorMessage, FiveStars, BubbleLoader, MovieImage, VideoPlayer)
- `:platform` — BaseViewModel, navigation, LoggerHelper
