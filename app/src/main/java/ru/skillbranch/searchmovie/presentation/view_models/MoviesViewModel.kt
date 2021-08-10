package ru.skillbranch.searchmovie.presentation.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ru.skillbranch.searchmovie.data.dto.CategoryDto
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.data.repository.CategoriesRepository
import ru.skillbranch.searchmovie.data.repository.MoviesRepository
import ru.skillbranch.searchmovie.data.sources.categories.CategoriesDataSourceImpl
import ru.skillbranch.searchmovie.data.sources.movies.MoviesDataSourceImpl

class MoviesViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MoviesRepository(MoviesDataSourceImpl())
    private val categoriesRepository: CategoriesRepository =
        CategoriesRepository(CategoriesDataSourceImpl())

    val moviesList: LiveData<List<MovieDto>>
        get() = _moviesList
    private val _moviesList = MutableLiveData<List<MovieDto>>()

    val categoriesList: LiveData<List<CategoryDto>>
        get() = _categoriesList
    private val _categoriesList = MutableLiveData<List<CategoryDto>>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d("CoroutineException", "Нет фильмов для отображения $exception")
    }

    init {
        _moviesList.postValue(moviesRepository.getMovies())
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

    fun getMovies(): List<MovieDto> {
        return moviesRepository.getMovies()
    }
}