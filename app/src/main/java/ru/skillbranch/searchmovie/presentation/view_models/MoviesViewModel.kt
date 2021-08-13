package ru.skillbranch.searchmovie.presentation.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.dto.CategoryDto
import ru.skillbranch.searchmovie.data.repository.CategoriesRepository
import ru.skillbranch.searchmovie.data.repository.MoviesRepository
import ru.skillbranch.searchmovie.data.sources.categories.CategoriesDataSourceImpl

class MoviesViewModel : ViewModel() {
    private val moviesRepository = MoviesRepository()
    private val categoriesRepository: CategoriesRepository =
        CategoriesRepository(CategoriesDataSourceImpl())

    val moviesList: LiveData<List<Movie>>
        get() = _moviesList
    private val _moviesList = MutableLiveData<List<Movie>>()

    val categoriesList: LiveData<List<CategoryDto>>
        get() = _categoriesList
    private val _categoriesList = MutableLiveData<List<CategoryDto>>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d("CoroutineException", "Нет фильмов для отображения $exception")
    }

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO + coroutineExceptionHandler) {
                _moviesList.postValue(moviesRepository.getMovies())
            }
        }
        _categoriesList.postValue(categoriesRepository.getCategories())
    }

    fun handleRefreshMovies() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val movies = moviesRepository.getRefreshMovies()
            _moviesList.postValue(movies)
        }
    }

    fun getCategories(): List<CategoryDto> {
        return categoriesRepository.getCategories()
    }

    fun getMovies(): LiveData<List<Movie>> {
        val result = MutableLiveData<List<Movie>>()
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            result.postValue(moviesRepository.getMovies())
        }
        return result
    }
}