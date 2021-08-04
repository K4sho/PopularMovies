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
    private var movieName: String? = null
    private var movieDescription: String? = null
    private var movieStarNumber: Int? = null
    private var movieAge: Int? = null
    private var movieImageUrl: String? = null
    private var actorImageUrl1: String? = null
    private var actorImageUrl2: String? = null
    private var actorImageUrl3: String? = null
    private var actorName1: String? = null
    private var actorName2: String? = null
    private var actorName3: String? = null

    private lateinit var moviePoster: ImageView
    private lateinit var firstActorImage: ImageView
    private lateinit var secondActorImage: ImageView
    private lateinit var thirdActorImage: ImageView
    private lateinit var movieNameTextView: TextView
    private lateinit var movieDescriptionTextView: TextView
    private lateinit var movieAgeTextView: TextView
    private lateinit var firstActorNameTextView: TextView
    private lateinit var secondActorNameTextView: TextView
    private lateinit var thirdActorNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieName = it.getString(MOVIE_NAME)
            movieDescription = it.getString(MOVIE_DESCRIPTION)
            movieStarNumber = it.getInt(MOVIE_RATE_SCORE)
            movieAge = it.getInt(MOVIE_AGE)
            movieImageUrl = it.getString(MOVIE_IMAGE_URL)
            actorImageUrl1 = it.getString(
                ACTOR_IMAGE_URL_1
            )
            actorImageUrl2 = it.getString(
                ACTOR_IMAGE_URL_2
            )
            actorImageUrl3 = it.getString(
                ACTOR_IMAGE_URL_3
            )
            actorName1 = it.getString(ACTOR_NAME_1)
            actorName2 = it.getString(ACTOR_NAME_2)
            actorName3 = it.getString(ACTOR_NAME_3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviePoster = view.findViewById(R.id.iv_banner)
        movieNameTextView = view.findViewById(R.id.tv_movie_title)
        movieDescriptionTextView = view.findViewById(R.id.tv_description_movie)
        movieAgeTextView = view.findViewById(R.id.tv_age_limit)
        firstActorImage = view.findViewById(R.id.iv_actor_1)
        secondActorImage = view.findViewById(R.id.iv_actor_2)
        thirdActorImage = view.findViewById(R.id.iv_actor_3)
        firstActorNameTextView = view.findViewById(R.id.tv_actor_1)
        secondActorNameTextView = view.findViewById(R.id.tv_actor_2)
        thirdActorNameTextView = view.findViewById(R.id.tv_actor_3)

        moviePoster.load(movieImageUrl)
        firstActorImage.load(actorImageUrl1)
        secondActorImage.load(actorImageUrl2)
        thirdActorImage.load(actorImageUrl3)
        firstActorNameTextView.text = actorName1
        secondActorNameTextView.text = actorName2
        thirdActorNameTextView.text = actorName3
        movieNameTextView.text = movieName
        movieDescriptionTextView.text = movieDescription
        movieAgeTextView.text = movieAge.toString() + "+"
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
        val maxScore = movieStarNumber ?: MAX_RATE_SCORE
        for (i in 0 until maxScore) {
            starImagesRating[i].setImageDrawable(iconStar)
        }
    }

    companion object {

        const val MAX_RATE_SCORE = 5
        const val MOVIE_NAME = "movieName"
        const val MOVIE_DESCRIPTION = "movieDescription"
        const val MOVIE_RATE_SCORE = "movieStarNumber"
        const val MOVIE_AGE = "movieAge"
        const val MOVIE_IMAGE_URL = "movieImageUrl"
        const val ACTOR_IMAGE_URL_1 = "actorImageURl1"
        const val ACTOR_IMAGE_URL_2 = "actorImageURl2"
        const val ACTOR_IMAGE_URL_3 = "actorImageURl3"
        const val ACTOR_NAME_1 = "actorName1"
        const val ACTOR_NAME_2 = "actorName2"
        const val ACTOR_NAME_3 = "actorName3"

        fun newInstance(movie: MovieDto) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_NAME, movie.title)
                    putString(MOVIE_DESCRIPTION, movie.description)
                    putInt(MOVIE_RATE_SCORE, movie.rateScore)
                    putInt(MOVIE_AGE, movie.ageLimit)
                    putString(MOVIE_IMAGE_URL, movie.imageUrl)
                    putString(ACTOR_IMAGE_URL_1, movie.actors[0].imageUrl)
                    putString(ACTOR_IMAGE_URL_2, movie.actors[1].imageUrl)
                    putString(ACTOR_IMAGE_URL_3, movie.actors[2].imageUrl)
                    putString(ACTOR_NAME_1, movie.actors[0].name)
                    putString(ACTOR_NAME_2, movie.actors[1].name)
                    putString(ACTOR_NAME_3, movie.actors[2].name)
                }
            }
    }
}
