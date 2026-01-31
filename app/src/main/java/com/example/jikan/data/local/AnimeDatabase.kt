package com.example.jikan.data.local


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AnimeEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao
}
