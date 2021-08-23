package ru.skillbranch.searchmovie.data.database.entities

import androidx.room.*

@Entity(tableName = "MoviesAndActors")
data class MoviesWithActors(
    @Embedded
    val movie: Movie,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "actor_id",
        associateBy = Junction(MovieActorCrossRef::class)
    )
    val actorsList: List<Actor>
)