package com.example.jikan.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime")
    suspend fun getAllAnime(): List<AnimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(list: List<AnimeEntity>)

    @Query("DELETE FROM anime")
    suspend fun clearAll()

    @Query("SELECT * FROM anime WHERE id = :id")
    suspend fun getAnimeById(id: Int): AnimeEntity?

    @Query("""
        UPDATE anime 
        SET synopsis = :synopsis, genres = :genres 
        WHERE id = :id
    """)
    suspend fun updateAnimeDetail(
        id: Int,
        synopsis: String?,
        genres: String?
    )

}
