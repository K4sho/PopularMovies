package ru.skillbranch.searchmovie

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import ru.skillbranch.searchmovie.data.database.MovieDatabase

class App : Application() {

    companion object {
        lateinit var database: MovieDatabase
        private var instance: App? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        database = MovieDatabase.getInstance(this)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Получим токен
            val token = task.result

            Log.d("FCM Token", "token - $token")
        })
    }
}