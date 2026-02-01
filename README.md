📺 Jikan Anime Explorer
A robust, offline-first Android application that brings the world of anime to your fingertips using
the Jikan API (MyAnimeList).


Reliability: The app shouldn't break just because the Wi-Fi does. I implemented a Room-based caching
strategy so users can browse their favorite titles anytime.

Architecture -> MVVM
Dependency Injection -> Hilt
Local Storage -> Room Database
API Integration -> Retrofit
Data flow -> Flows
Image Loading -> Glide
Asynchronous tasks -> Coroutines


Performance: By using StateFlow and Coroutines, the UI stays reactive and smooth, only updating when
the data actually changes.

🚀 Key Features
🏠 Browse with Ease
Anime List: Instantly see list of anime from the API.

Smart Refresh: Pull-to-refresh resets the whole data base and updates it with the fresh data fetched from
API.


📄 Deep-Dive Details
Trailer Integration: Integrated the Android YouTube Player to let users watch trailers directly
in-app.

Want to see more about the Anime? Tapping the title takes you directly to the official MyAnimeList page.

Detail Caching: Once you view an anime, its synopsis and genres are saved locally for offline
reading.

Assumptions:
Please check the code in the master branch

User has internet on at least once before using the app

Features Implemented:
1. Anime Listing Screen
2. Anime Detail Screen
3. Inbuilt Youtube player
4. Redirection to web if clicked on detail in Detail Screen.
5. Room Database for local storage of both list and detail.
6. Once user refreshes the list screen any detail cached data is cleared and fresh data from server is inserted.
