package ru.skillbranch.searchmovie.presentation.fragments.listeners

import ru.skillbranch.searchmovie.data.dto.MovieDto

interface MovieClickListener {
    fun onMovieClick(movieId: Int)
}