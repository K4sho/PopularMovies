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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.presentation.view_models.MovieDetailsViewModel

class MovieDetailsFragment : Fragment() {
    private var movieId: Int = 0

    private lateinit var moviePoster: ImageView
    private lateinit var movieGenre: TextView
    private lateinit var firstActorImage: ImageView
    private lateinit var secondActorImage: ImageView
    private lateinit var thirdActorImage: ImageView
    private lateinit var movieNameTextView: TextView
    private lateinit var movieDescriptionTextView: TextView
    private lateinit var movieAgeTextView: TextView
    private lateinit var movieReleaseDate: TextView
    private lateinit var firstActorNameTextView: TextView
    private lateinit var secondActorNameTextView: TextView
    private lateinit var thirdActorNameTextView: TextView
    private lateinit var fragmentView: View

    private lateinit var viewModel: MovieDetailsViewModel

    private val moviesInfoObserver = Observer { movie: MovieDto ->
        moviePoster = fragmentView.findViewById(R.id.iv_banner)
        movieGenre = fragmentView.findViewById(R.id.tv_genre)
        movieNameTextView = fragmentView.findViewById(R.id.tv_movie_title)
        movieDescriptionTextView = fragmentView.findViewById(R.id.tv_description_movie)
        movieAgeTextView = fragmentView.findViewById(R.id.tv_age_limit)
        firstActorImage = fragmentView.findViewById(R.id.iv_actor_1)
        secondActorImage = fragmentView.findViewById(R.id.iv_actor_2)
        thirdActorImage = fragmentView.findViewById(R.id.iv_actor_3)
        firstActorNameTextView = fragmentView.findViewById(R.id.tv_actor_1)
        secondActorNameTextView = fragmentView.findViewById(R.id.tv_actor_2)
        thirdActorNameTextView = fragmentView.findViewById(R.id.tv_actor_3)
        movieReleaseDate = fragmentView.findViewById(R.id.tv_date_movie)

        moviePoster.load(movie.imageUrl)
        movieGenre.text = movie.genre.name
        firstActorImage.load(movie.actors[0].imageUrl)
        secondActorImage.load(movie.actors[1].imageUrl)
        thirdActorImage.load(movie.actors[2].imageUrl)
        firstActorNameTextView.text = movie.actors[0].name
        secondActorNameTextView.text = movie.actors[1].name
        thirdActorNameTextView.text = movie.actors[2].name
        movieNameTextView.text = movie.title
        movieDescriptionTextView.text = movie.description
        movieReleaseDate.text = movie.releaseDate
        movieAgeTextView.text = movie.ageLimit.toString() + "+"
        val iconStar = ResourcesCompat.getDrawable(
            fragmentView.context.resources,
            R.drawable.ic_star_selected,
            null
        )
        val starImagesRating = listOf<ImageView>(
            fragmentView.findViewById(R.id.rating_star_1),
            fragmentView.findViewById(R.id.rating_star_2),
            fragmentView.findViewById(R.id.rating_star_3),
            fragmentView.findViewById(R.id.rating_star_4),
            fragmentView.findViewById(R.id.rating_star_5)
        )
        val maxScore = movie.rateScore ?: MAX_RATE_SCORE
        for (i in 0 until maxScore) {
            starImagesRating[i].setImageDrawable(iconStar)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(MOVIE_ID)
        }
        viewModel = ViewModelProvider(requireActivity()).get(MovieDetailsViewModel::class.java)
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
        fragmentView = view
        viewModel.getMovieByIdWithActors(movieId).observe(requireActivity(), moviesInfoObserver)
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