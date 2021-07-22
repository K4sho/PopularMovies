package ru.skillbranch.searchmovie.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.presentation.fragments.MoviesFragment
import ru.skillbranch.searchmovie.presentation.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {

    private var startFragment: Fragment? = null
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            startFragment = MoviesFragment.newInstance()
            startFragment?.apply {
                supportFragmentManager.beginTransaction().add(
                    R.id.main_fragment_container,
                    this, INITIAL_FRAGMENT_TAG
                ).commit()
            }
        } else {
            startFragment = supportFragmentManager.findFragmentByTag(INITIAL_FRAGMENT_TAG)
        }

        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_menu_home -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_fragment_container,
                        MoviesFragment.newInstance()
                    ).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_menu_profile -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_fragment_container,
                        ProfileFragment.newInstance()
                    ).commit()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    companion object {
        const val INITIAL_FRAGMENT_TAG = "InitialFragment"
    }
}