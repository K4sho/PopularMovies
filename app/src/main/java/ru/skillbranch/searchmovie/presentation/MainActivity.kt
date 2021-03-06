package ru.skillbranch.searchmovie.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.notifications.PushService

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var pushBroadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_fragment_container)

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
}