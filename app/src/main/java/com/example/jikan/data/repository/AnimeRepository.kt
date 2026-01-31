package com.example.jikan.data.repository

import android.content.Context
import com.example.jikan.data.local.AnimeDao
import com.example.jikan.data.local.toAnimeDetail
import com.example.jikan.data.local.toEntity
import com.example.jikan.data.local.toUiModel
import com.example.jikan.data.remote.APIService
import com.example.jikan.model.Anime
import com.example.jikan.model.AnimeDetail
import com.example.jikan.util.NetworkUtils
import com.example.jikan.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val apiService: APIService,
    private val animeDao: AnimeDao,
    private val context: Context
) {


    fun getTopAnime(): Flow<Result<Anime>> = flow {
        emit(Result.Loading)

        if (NetworkUtils.isInternetAvailable(context)) {
            try {
                val response = apiService.getTopAnime()

                animeDao.clearAll()
                animeDao.insertAnime(response.data.map { it.toEntity() })

                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error("Failed to load anime list", e))
            }
        } else {
            val cached = animeDao.getAllAnime()
            if (cached.isNotEmpty()) {
                emit(
                    Result.Success(
                        Anime(data = cached.map { it.toUiModel() })
                    )
                )
            } else {
                emit(Result.Error("No offline data available"))
            }
        }
    }


    fun getAnimeDetail(id: Int): Flow<Result<AnimeDetail>> = flow {
        emit(Result.Loading)

        if (NetworkUtils.isInternetAvailable(context)) {
            try {
                val response = apiService.getAnimeById(id).data

                animeDao.updateAnimeDetail(
                    id = id,
                    synopsis = response.synopsis,
                    genres = response.genres?.joinToString(", ") { it.name }
                )

                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error("Failed to load anime detail", e))
            }
        } else {
            val cached = animeDao.getAnimeById(id)
            if (cached != null && cached.synopsis != null) {
                emit(Result.Success(cached.toAnimeDetail()))
            } else {
                emit(Result.Error("No offline detail available"))
            }
        }
    }
}
