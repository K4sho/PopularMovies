package ru.skillbranch.searchmovie.data.remote_res

import com.google.gson.annotations.SerializedName

data class CastActorRes(
    @SerializedName("name") var nameActor: String,
    @SerializedName("profile_path") var actorPoster: String?,
    @SerializedName("credit_id") var idActor: String
)

data class ActorRes(
    @SerializedName("id") var id: Int,
    @SerializedName("cast") var castPerson: List<CastActorRes>,
)