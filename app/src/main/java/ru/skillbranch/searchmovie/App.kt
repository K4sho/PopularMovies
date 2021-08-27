package ru.skillbranch.searchmovie

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import ru.skillbranch.searchmovie.data.database.MovieDatabase
import ru.skillbranch.searchmovie.data.network.ApiService
import ru.skillbranch.searchmovie.data.network.NetworkService

class App : Application() {

    companion object {
        lateinit var database: MovieDatabase
        lateinit var apiHelper: ApiService
        private var instance: App? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        // Проверка соединения
        val isNetworkActive: Boolean
            get() {
                val connectivityManager = applicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
                    }
                }
                return false
            }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        database = MovieDatabase.getInstance(this)
        apiHelper = NetworkService.getJSONApi()

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