package ru.skillbranch.searchmovie.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.skillbranch.searchmovie.AppConfig.TAG_WORKMANAGER_UPDATING_MOVIES
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.background.UpdateMoviesWorker
import ru.skillbranch.searchmovie.notifications.PushService
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var pushBroadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_fragment_container)

        /// Запустим WorkManager
        runUpdatingMoviesManager()

        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.nav_movies_fragment -> {
                    if (navController.currentDestination?.id == R.id.nav_movies_fragment) {
                        navController.popBackStack(R.id.nav_movies_fragment, false)
                    } else if (navController.currentDestination?.id == R.id.nav_movies_details_fragment) {
                        navController.popBackStack(R.id.nav_movies_details_fragment, true)
                        navController.navigate(R.id.nav_movies_fragment)
                    }
                }
            }
        }

        navController.addOnDestinationChangedListener { controller, destination, args ->
            if (destination.id == R.id.logInFragment) {
                bottomNavigationView.visibility = View.INVISIBLE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }

        pushBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val extras = intent?.extras
                extras?.keySet()?.firstOrNull() { it == PushService.KEY_ACTION }?.let { key ->
                    when (extras.getString(key)) {
                        PushService.ACTION_SHOW_MESSAGE -> {
                            extras.getString(PushService.KEY_MESSAGE)?.let { message ->
                                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        else -> Log.e("BroadcastReceiver", "No needed key found")
                    }
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(PushService.INTENT_FILTER)

        registerReceiver(pushBroadcastReceiver, intentFilter)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id != R.id.nav_movies_fragment &&
            bottomNavigationView.selectedItemId != R.id.nav_movies_fragment
        ) {
            bottomNavigationView.selectedItemId = R.id.nav_movies_fragment
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(pushBroadcastReceiver)
    }

    private fun runUpdatingMoviesManager() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val periodicRequest =
            PeriodicWorkRequest.Builder(UpdateMoviesWorker::class.java, 1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            TAG_WORKMANAGER_UPDATING_MOVIES,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
    }

}