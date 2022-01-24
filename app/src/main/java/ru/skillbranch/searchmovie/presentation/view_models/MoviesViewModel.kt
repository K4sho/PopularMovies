package ru.skillbranch.searchmovie.presentation.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.repository.MoviesRepository
import java.lang.Exception

class MoviesViewModel : ViewModel() {
    enum class LoadingDataState {
        UNKNOWN,
        LOADING,
        FINISHED,
        ERROR
    }

    private val moviesRepository = MoviesRepository()

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = _moviesList

    private val _loadingDataState = MutableLiveData<LoadingDataState>()
    val loadingDataState: LiveData<LoadingDataState>
        get() = _loadingDataState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _moviesList.postValue(listOf())
        Log.d("CoroutineException", "Нет фильмов для отображения $exception")
    }

    init {
        _loadingDataState.value = LoadingDataState.UNKNOWN
        _moviesList.postValue(emptyList())

        // Заполняем данными из БД
        viewModelScope.launch {
            withContext(Dispatchers.IO + coroutineExceptionHandler) {
                try {
                    _loadingDataState.postValue(LoadingDataState.LOADING)
                    _moviesList.postValue(moviesRepository.getMovies())
                    _loadingDataState.postValue(LoadingDataState.FINISHED)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _loadingDataState.postValue(LoadingDataState.ERROR)
                }
            }
        }
    }

    fun handleRefreshMoviesFromApi() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
                _loadingDataState.postValue(LoadingDataState.LOADING)
                _moviesList.postValue(moviesRepository.getMoviesFromApi())
                _loadingDataState.postValue(LoadingDataState.FINISHED)
            } catch (e: Exception) {
                e.printStackTrace()
                _loadingDataState.postValue(LoadingDataState.ERROR)
            }
        }
    }
}