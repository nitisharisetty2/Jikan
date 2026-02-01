# Jikan Anime Explorer

Android application using Jikan API 
Offline-first anime browser with local caching.

## Features Implemented

1. Anime Listing Screen
    - Displays a list of anime fetched from the Jikan API
    - Pull-to-refresh functionality to update the list with fresh data
    - Basic loading and error states

2. Anime Detail Screen
    - Shows detailed information: title, synopsis, genres, score, etc.
    - Embedded YouTube player for watching anime trailers directly in the app
    - Button to redirect to the official MyAnimeList page in the browser

3. Offline Support via Room Database
    - Caches the anime list for offline browsing
    - Caches individual anime details (synopsis, genres, etc.) once viewed
    - Data remains available offline after at least one successful online fetch

4. Data Refresh Behavior
    - Pull-to-refresh on the listing screen clears all cached data (both list and details)
    - Fetches fresh data from the API and repopulates the database

5. Modern Android Tech Stack
    - Architecture: MVVM
    - Dependency Injection: Hilt
    - Networking: Retrofit
    - Data flow: Kotlin Flows / StateFlow
    - Image loading: Glide
    - Asynchronous operations: Coroutines
    - Local persistence: Room

## Assumptions Made

- The user has an internet connection at least once after installing the app (to populate the
  initial cache).
- After the first successful fetch, the app can be used mostly offline (list + viewed details).
- YouTube trailer URLs provided by the API are valid and playable.
- Basic error handling is sufficient (network errors show a message; no advanced retry mechanisms).

## Known Limitations

- No pagination / infinite scrolling — only the initial/top anime list is fetched.
- No search functionality for finding specific anime titles.
- No sorting or filtering options on the anime list (e.g., by score, genre, season).
- Cached data is fully cleared on every pull-to-refresh (no smart/partial refresh).
- No dark mode support.
- Limited error recovery — if the API is down after initial cache, offline mode still works but
  fresh data cannot be obtained.
- Trailer playback works only if the URL works.

This project demonstrates core modern Android development practices with a focus on offline-first
design using Room caching, MVVM, Hilt, Retrofit, Coroutines, and Flows.