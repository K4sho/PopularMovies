package ru.skillbranch.searchmovie.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.presentation.view_models.MovieDetailsViewModel

class MovieDetailsFragment : Fragment() {
    private var movieId: Int = 0

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

    private lateinit var movie: MovieDto
    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(MOVIE_ID)
        }
        viewModel = ViewModelProvider(requireActivity()).get(MovieDetailsViewModel::class.java)
        movie = viewModel.getMoviesById(movieId) ?: viewModel.getMovies()[0]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    @SuppressLint("SetTextI18n")
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

        moviePoster.load(movie.imageUrl)
        firstActorImage.load(movie.actors[0].imageUrl)
        secondActorImage.load(movie.actors[1].imageUrl)
        thirdActorImage.load(movie.actors[2].imageUrl)
        firstActorNameTextView.text = movie.actors[0].name
        secondActorNameTextView.text = movie.actors[1].name
        thirdActorNameTextView.text = movie.actors[2].name
        movieNameTextView.text = movie.title
        movieDescriptionTextView.text = movie.description
        movieAgeTextView.text = movie.ageLimit.toString() + "+"
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
        val maxScore = movie.rateScore ?: MAX_RATE_SCORE
        for (i in 0 until maxScore) {
            starImagesRating[i].setImageDrawable(iconStar)
        }
    }

    companion object {

        const val MOVIE_ID = "movieId"
        const val MAX_RATE_SCORE = 5

        fun newInstance(bundle: Bundle) =
            MovieDetailsFragment().apply {
                arguments = bundle
            }
    }
}
