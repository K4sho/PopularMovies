package ru.skillbranch.searchmovie.data.utils

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import ru.skillbranch.searchmovie.data.database.entities.Actor
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.database.entities.MoviesWithActors
import ru.skillbranch.searchmovie.data.dto.ActorDto
import ru.skillbranch.searchmovie.data.dto.CategoryDto
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.data.remote_res.ActorRes
import ru.skillbranch.searchmovie.data.remote_res.CastActorRes
import ru.skillbranch.searchmovie.data.remote_res.MovieRes
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

fun MovieRes.toMovieDb(): Movie {
    return Movie(
        movieId = this.idMovie,
        title = this.originalTitle,
        description = this.overview ?: "",
        rateScore = ceil(this.voteAverage).toInt(),
        ageLimit = if ((this.adult != null) and (this.adult == true)) 18 else 0,
        imageUrl = this.posterImagePath ?: "", //TODO: Нужно будет как-то конвертнуть,
        genre = CategoryDto(this.genreIds?.first() ?: 0, ""),
        releaseDate = this.releaseDate ?: "00.00.0000"
    )
}

fun CastActorRes.toActorDb(): Actor {
    return Actor(
        actorId = this.idActor.toInt(),
        actorName = this.nameActor,
        photo =  this.actorPoster ?: ""
    )
}