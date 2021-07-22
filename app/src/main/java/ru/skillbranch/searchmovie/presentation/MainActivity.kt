package ru.skillbranch.searchmovie.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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
                supportFragmentManager.beginTransaction().add(R.id.main_fragment_container,
                    this, INITIAL_FRAGMENT_TAG).commit()
            }
        } else {
            startFragment = supportFragmentManager.findFragmentByTag(INITIAL_FRAGMENT_TAG)
        }

        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_menu_home -> {
                    loadFragment(MoviesFragment.newInstance())
                    true
                }
                R.id.bottom_menu_profile -> {
                    loadFragment(ProfileFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment_container, fragment).
            commit()
        }
    }

    companion object {
        const val INITIAL_FRAGMENT_TAG = "InitialFragment"
    }
}