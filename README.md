📺 Jikan Anime Explorer
A robust, offline-first Android application that brings the world of anime to your fingertips using
the Jikan API (MyAnimeList).


Reliability: The app shouldn't break just because the Wi-Fi does. I implemented a Room-based caching
strategy so users can browse their favorite titles anytime.

Scalability: Using MVVM, Hilt, Clean Architecture, Coroutines for asynchronous work, flows for
reactive approach

Performance: By using StateFlow and Coroutines, the UI stays reactive and smooth, only updating when
the data actually changes.

🚀 Key Features
🏠 Browse with Ease
Top Anime Feed: Instantly see what's trending globally.

Smart Refresh: Pull-to-refresh doesn't just clear the screen; it intelligently updates the local
cache.

Visual Polish: Leveraged Glide for smooth image loading and error fallbacks.

📄 Deep-Dive Details
Trailer Integration: Integrated the Android YouTube Player to let users watch trailers directly
in-app.

Web Sync: Want to see more? Tapping the title takes you directly to the official MyAnimeList page.

Detail Caching: Once you view an anime, its synopsis and genres are saved locally for offline
reading.