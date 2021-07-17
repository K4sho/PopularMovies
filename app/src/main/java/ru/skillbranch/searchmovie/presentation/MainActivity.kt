package ru.skillbranch.searchmovie.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.skillbranch.searchmovie.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
    }
}