package com.example.jikan.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimeDao {


    @Query("SELECT * FROM anime")
    suspend fun getAllAnime(): List<AnimeEntity>

    @Query("SELECT * FROM anime WHERE id = :id")
    suspend fun getAnimeById(id: Int): AnimeEntity?



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnimeList(list: List<AnimeEntity>)

    @Query("""
        UPDATE anime SET
            title = :title,
            imageUrl = :imageUrl,
            episodes = :episodes,
            rating = :rating
        WHERE id = :id
    """)
    suspend fun updateAnimeListFields(
        id: Int,
        title: String?,
        imageUrl: String?,
        episodes: Int?,
        rating: Double?
    )


    @Query("""
        UPDATE anime SET
            synopsis = :synopsis,
            genres = :genres
        WHERE id = :id
    """)
    suspend fun updateAnimeDetail(
        id: Int,
        synopsis: String?,
        genres: String?
    )

    @Query("DELETE FROM anime")
    suspend fun clearAll()

}
