package ru.skillbranch.searchmovie.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import coil.load
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.data.repository.MoviesRepository
import ru.skillbranch.searchmovie.data.sources.movies.MoviesDataSourceImpl

class MovieDetailsFragment : Fragment() {
    private var position: Int = 0
    private var movies = MoviesRepository(MoviesDataSourceImpl())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt(MOVIE_POSITION) ?: 0
        val movie = movies.getMovies()[position]
        val ageLimit = "${movie.ageLimit}+"
        view.apply {
            findViewById<ImageView>(R.id.iv_banner).load(movie.imageUrl)
            findViewById<TextView>(R.id.tv_genre).text = movie.genre.name.lowercase()
            findViewById<TextView>(R.id.tv_date_movie).text = movie.releaseDate
            findViewById<TextView>(R.id.tv_age_limit).text = ageLimit
            findViewById<TextView>(R.id.tv_movie_title).text = movie.title
            findViewById<TextView>(R.id.tv_description_movie).text = movie.description
            findViewById<ImageView>(R.id.iv_actor_1).load(movie.actors[0].imageUrl)
            findViewById<TextView>(R.id.tv_actor_1).text = movie.actors[0].name
            findViewById<ImageView>(R.id.iv_actor_2).load(movie.actors[1].imageUrl)
            findViewById<TextView>(R.id.tv_actor_2).text = movie.actors[1].name
            findViewById<ImageView>(R.id.iv_actor_3).load(movie.actors[2].imageUrl)
            findViewById<TextView>(R.id.tv_actor_3).text = movie.actors[2].name
        }
        val iconStar = ResourcesCompat.getDrawable(
            view.context.resources,
            R.drawable.ic_star_selected,
            null
        )
        val starImagesRating = listOf<ImageView>(
            view.findViewById(R.id.rating_star_1),
            view.findViewById(R.id.rating_star_2),
            view.findViewById(R.id.rating_star_3),
            view.findViewById(R.id.rating_star_4),
            view.findViewById(R.id.rating_star_5)
        )
        val maxScore = if (movie.rateScore < 6) movie.rateScore else MAX_RATE_SCORE
        for (i in 0 until maxScore) {
            starImagesRating[i].setImageDrawable(iconStar)
        }
    }

    companion object {

        const val MAX_RATE_SCORE = 5
        const val MOVIE_POSITION = "moviePosition"

        fun newInstance(moviePosition: Int): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            val args = Bundle()
            args.putInt(MOVIE_POSITION, moviePosition)
            fragment.arguments = args
            return fragment
        }
    }
}
