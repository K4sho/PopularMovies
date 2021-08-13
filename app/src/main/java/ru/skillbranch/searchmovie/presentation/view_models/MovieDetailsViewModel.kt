package ru.skillbranch.searchmovie.presentation.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.database.entities.MoviesWithActors
import ru.skillbranch.searchmovie.data.dto.CategoryDto
import ru.skillbranch.searchmovie.data.repository.MoviesRepository

class MovieDetailsViewModel() : ViewModel() {
    private val moviesRepository = MoviesRepository()

    fun getMovieByIdWithActors(movieId: Int) : LiveData<MoviesWithActors> {
        val result = MutableLiveData<MoviesWithActors>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                result.postValue(moviesRepository.getMovieWitchActors(movieId))
            }
        }
        return result
    }
}