package com.example.jikan.data.remote

import com.example.jikan.model.Anime
import com.example.jikan.model.AnimeDetail
import com.example.jikan.model.AnimeDetailData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {
    @GET("top/anime")
    suspend fun getTopAnime(): Anime

    @GET("anime/{id}")
    suspend fun getAnimeById(
        @Path("id") id: Int
    ): AnimeDetailData
}