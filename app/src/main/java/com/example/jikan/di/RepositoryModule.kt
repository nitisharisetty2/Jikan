package com.example.jikan.di

import android.content.Context
import com.example.jikan.data.local.AnimeDao
import com.example.jikan.data.remote.APIService
import com.example.jikan.data.repository.AnimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAnimeRepository(
        apiService: APIService,
        animeDao: AnimeDao,
        @ApplicationContext context: Context
    ): AnimeRepository {
        return AnimeRepository(apiService, animeDao, context)
    }
}
