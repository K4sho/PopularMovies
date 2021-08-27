package ru.skillbranch.searchmovie.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ru.skillbranch.searchmovie.App
import ru.skillbranch.searchmovie.data.database.entities.Actor
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.repository.MoviesRepository

class MovieDetailsViewModel() : ViewModel() {
    private val moviesRepository = MoviesRepository()

    enum class LoadingDataState {
        UNKNOWN,
        LOADING,
        FINISHED,
        ERROR
    }

    private var _movie: MutableLiveData<Movie> = MutableLiveData()
    val movie: LiveData<Movie> get() = _movie

    private var _actors: MutableLiveData<List<Actor>> = MutableLiveData()
    val actors: LiveData<List<Actor>> get() = _actors

    val loadingDataState: LiveData<LoadingDataState>
        get() = _loadingDataState
    private val _loadingDataState = MutableLiveData<LoadingDataState>()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _actors.postValue(listOf())
    }

    fun getMovieByIdWithActors(movieId: Int) {
        viewModelScope.launch {
            withContext(SupervisorJob() + Dispatchers.IO + exceptionHandler) {
                // Актеры уже должны быть в БД
                _loadingDataState.postValue(LoadingDataState.LOADING)
                val movieWithActors = moviesRepository.getMovieWithActorsFromDB(movieId)
                _movie.postValue(movieWithActors.movie)
                if (movieWithActors.actorsList.isEmpty() && App.isNetworkActive) {
                    try {
                        // Если актеров не было в БД к этому фильму и у нас есть инет, то берем актеров с API
                        val actors = moviesRepository.getMovieActorsFromAPI(movieId)
                        _actors.postValue(actors)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        _loadingDataState.postValue(LoadingDataState.ERROR)
                    }
                } else {
                    // А если актеры были, то их и ставим без обращения к API
                    _actors.postValue(movieWithActors.actorsList)
                    _loadingDataState.postValue(LoadingDataState.FINISHED)
                }
            }
        }
    }
}