package ru.skillbranch.searchmovie.data.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.skillbranch.searchmovie.data.repository.MoviesRepository

class UpdateMoviesWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val movieRepository = MoviesRepository()
            val data = movieRepository.getMoviesFromApi()
            if (data.isEmpty()) {
                Result.retry()
            } else {
                Result.success()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}