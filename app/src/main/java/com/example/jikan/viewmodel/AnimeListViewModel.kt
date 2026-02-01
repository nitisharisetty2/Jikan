package com.example.jikan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikan.data.repository.AnimeRepository
import com.example.jikan.model.Anime
import com.example.jikan.model.AnimeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.jikan.util.Result



@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {


    private val _animeListState =
        MutableStateFlow<Result<Anime>>(Result.Loading)
    val animeState: StateFlow<Result<Anime>> = _animeListState


    private val _animeDetailState =
        MutableStateFlow<Result<AnimeDetail>>(Result.Loading)
    val animeDetailState: StateFlow<Result<AnimeDetail>> = _animeDetailState


    fun fetchAnimeList() {
        viewModelScope.launch {
            repository.getTopAnime().collect {
                _animeListState.value = it
            }
        }
    }

    fun fetchAnimeDetail(id: Int) {
        viewModelScope.launch {
            repository.getAnimeDetail(id).collect {
                _animeDetailState.value = it
            }
        }
    }
}
