package ru.skillbranch.searchmovie.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.skillbranch.searchmovie.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

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
}