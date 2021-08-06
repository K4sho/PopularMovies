package ru.skillbranch.searchmovie.presentation.view_models

import androidx.lifecycle.ViewModel
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.data.repository.MoviesRepository
import ru.skillbranch.searchmovie.data.sources.movies.MoviesDataSourceImpl

class MovieDetailsViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MoviesRepository(MoviesDataSourceImpl())

    fun getMoviesById(movieId: Int): MovieDto? {
        return moviesRepository.getMovieById(movieId)
    }
}