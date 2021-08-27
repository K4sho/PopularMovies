package ru.skillbranch.searchmovie.data.utils


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Environment
import com.bumptech.glide.Glide
import ru.skillbranch.searchmovie.AppConfig.IMAGE_URL
import ru.skillbranch.searchmovie.AppConfig.NAME_OF_IMAGES_DIR
import ru.skillbranch.searchmovie.AppConfig.QUALITY_OF_IMAGES
import ru.skillbranch.searchmovie.data.database.entities.Actor
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.remote_res.CastActorRes
import ru.skillbranch.searchmovie.data.remote_res.MovieRes
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


fun saveImage(context: Context, image: Bitmap, firstHeader: String, secondHeader: String): String? {
    var savedImagePath: String? = null
    val imageFileName = "MovieApp_${firstHeader}_${secondHeader}.jpg"
    val storageDir = File(
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            .toString() + "/${NAME_OF_IMAGES_DIR}"
    )
    var success = true
    if (!storageDir.exists()) {
        success = storageDir.mkdirs()
    }
    if (success) {
        val imageFile = File(storageDir, imageFileName)
        savedImagePath = imageFile.absolutePath
        try {
            val fOut: OutputStream = FileOutputStream(imageFile)
            image.compress(Bitmap.CompressFormat.JPEG, QUALITY_OF_IMAGES, fOut)
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return savedImagePath
}

fun MovieRes.toMovieDb(context: Context): Movie {
    var pathPoster = ""
    if (this.posterImagePath != null) {
        pathPoster = saveImage(
            context,
            Glide
                .with(context)
                .asBitmap()
                .load(IMAGE_URL + this.posterImagePath)
                .placeholder(ColorDrawable(Color.parseColor("#C4C4C4")))
                .error(ColorDrawable(Color.RED))
                .submit()
                .get(),
            this.title,
            "Poster"
        )!!
    }
    var pathBackgroundImage = ""
    if (this.backgroundImagePath != null) {
        pathBackgroundImage = saveImage(
            context,
            Glide
                .with(context)
                .asBitmap()
                .load(IMAGE_URL + this.backgroundImagePath)
                .placeholder(ColorDrawable(Color.parseColor("#C4C4C4")))
                .error(ColorDrawable(Color.RED))
                .submit()
                .get(),
            this.title,
            "Background"
        )!!
    }

    return Movie(
        movieId = this.idMovie,
        releaseDate = this.releaseDate ?: "",
        title = this.title,
        imageUrl = pathPoster,
        backgroundImage = pathBackgroundImage,
        description = this.overview ?: "",
        rateScore = (this.voteAverage / 2).toInt(),
        ageLimit = if (this.adult == true) 18 else 0,
        genresIds = this.genreIds ?: listOf()
    )
}

fun CastActorRes.toActorDb(context: Context): Actor {
    var path = ""
    if (this.actorPoster != null) {
        path = saveImage(
            context,
            Glide.with(context)
                .asBitmap()
                .load(IMAGE_URL + this.actorPoster)
                .placeholder(ColorDrawable(Color.parseColor("#C4C4C4")))
                .error(ColorDrawable(Color.RED))
                .submit()
                .get(),
            "Actor ${this.nameActor}",
            this.idActor
        )!!
    }
    return Actor(actorId = this.idActor, actorName = this.nameActor, photo = path)
}