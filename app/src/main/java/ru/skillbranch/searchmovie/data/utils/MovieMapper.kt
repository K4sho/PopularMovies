package ru.skillbranch.searchmovie.data.utils

import ru.skillbranch.searchmovie.data.database.entities.Actor
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.database.entities.MoviesWithActors
import ru.skillbranch.searchmovie.data.dto.ActorDto
import ru.skillbranch.searchmovie.data.dto.CategoryDto
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.data.remote_res.MovieRes
import ru.skillbranch.searchmovie.data.remote_res.ResultMoviesList
import kotlin.math.ceil


fun Actor.toActorDto(): ActorDto {
    return ActorDto(
        name = this.actorName,
        imageUrl = this.photo
    )
}

fun Movie.toMovie(): MovieDto {
    return MovieDto(
        id = this.movieId,
        title = this.title,
        description = this.description,
        rateScore = this.rateScore,
        ageLimit = this.ageLimit,
        imageUrl = this.imageUrl,
        genre = this.genre,
        releaseDate = this.releaseDate,
        actors = emptyList()
    )
}

fun MoviesWithActors.toMovie(): MovieDto {
    val actors = mutableListOf<ActorDto>()
    for (actor in this.actorsList) {
        actors.add(actor.toActorDto())
    }
    return MovieDto(
        id = this.movie.movieId,
        title = this.movie.title,
        description = this.movie.description,
        rateScore = this.movie.rateScore,
        ageLimit = this.movie.ageLimit,
        imageUrl = this.movie.imageUrl,
        genre = this.movie.genre,
        releaseDate = this.movie.releaseDate,
        actors = actors
    )
}

fun ResultMoviesList.toMovieDb(): Movie {
    return Movie(
        movieId = this.id,
        title = this.original_title,
        description = this.overview,
        rateScore = ceil(this.vote_average).toInt(),
        ageLimit = if (this.adult) 18 else 0,
        imageUrl = this.poster_path, //TODO: Нужно будет как-то конвертнуть,
        genre = CategoryDto(this.genre_ids.first(), ""),
        releaseDate = this.release_date
    )
}