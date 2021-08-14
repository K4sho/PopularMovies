package ru.skillbranch.searchmovie.data.utils

import ru.skillbranch.searchmovie.data.database.entities.Actor
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.database.entities.MoviesWithActors
import ru.skillbranch.searchmovie.data.dto.ActorDto
import ru.skillbranch.searchmovie.data.dto.CategoryDto
import ru.skillbranch.searchmovie.data.dto.MovieDto


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