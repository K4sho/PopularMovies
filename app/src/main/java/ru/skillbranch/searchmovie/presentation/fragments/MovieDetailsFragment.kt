package ru.skillbranch.searchmovie.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.database.entities.Actor
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.utils.genresList
import ru.skillbranch.searchmovie.presentation.recycler_views.adapters.ActorsCastAdapter
import ru.skillbranch.searchmovie.presentation.recycler_views.decorations.ActorsItemDecoration
import ru.skillbranch.searchmovie.presentation.view_models.MovieDetailsViewModel
import ru.skillbranch.searchmovie.presentation.view_models.MoviesViewModel

class MovieDetailsFragment : Fragment() {
    private var movieId: Int = 0
    private val actorsCastAdapter = ActorsCastAdapter()

    private lateinit var moviePoster: ImageView
    private lateinit var movieGenre: TextView
    private lateinit var movieNameTextView: TextView
    private lateinit var movieDescriptionTextView: TextView
    private lateinit var movieAgeTextView: TextView
    private lateinit var movieReleaseDate: TextView
    private lateinit var fragmentView: View
    private lateinit var actorsRecyclerView: RecyclerView
    private val viewModel: MovieDetailsViewModel by viewModels()

    private lateinit var stateObserver: Observer<MovieDetailsViewModel.LoadingDataState>
    private lateinit var actorsInfoObserver: Observer<List<Actor>>
    private lateinit var movieInfoObserver: Observer<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /// Анимация
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.movie_poster_transition)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.movie_poster_transition)
        arguments?.let {
            movieId = it.getInt(MOVIE_ID)
        }
        stateObserver = Observer { state: MovieDetailsViewModel.LoadingDataState ->
            when (state) {
                MovieDetailsViewModel.LoadingDataState.ERROR -> {
                    Snackbar.make(
                        requireActivity().findViewById(R.id.container),
                        "Не удалось загрузить детали фильма",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                MovieDetailsViewModel.LoadingDataState.LOADING -> {
                    Snackbar.make(
                        requireActivity().findViewById(R.id.container),
                        "Загрузка данных о актерах",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                MovieDetailsViewModel.LoadingDataState.FINISHED -> {
                }
                else -> {
                    Snackbar.make(
                        requireActivity().findViewById(R.id.container),
                        "Сбой при попытке загрузить детали фильма",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        actorsInfoObserver = Observer { actors: List<Actor> ->
            setActorsCastRecycleView(actors)
        }

        movieInfoObserver = Observer { movie: Movie ->
            moviePoster = fragmentView.findViewById(R.id.iv_banner)
            movieGenre = fragmentView.findViewById(R.id.tv_genre)
            movieNameTextView = fragmentView.findViewById(R.id.tv_movie_title)
            movieDescriptionTextView = fragmentView.findViewById(R.id.tv_description_movie)
            movieAgeTextView = fragmentView.findViewById(R.id.tv_age_limit)
            movieReleaseDate = fragmentView.findViewById(R.id.tv_date_movie)

            /// Устанавливаем фон
            if (movie.backgroundImage.isEmpty()) {
                Glide
                    .with(requireContext())
                    .load(R.drawable.placeholder_bg)
                    .into(moviePoster)
            } else {
                Glide
                    .with(requireContext())
                    .load(movie.backgroundImage)
                    .into(moviePoster)
            }
            movieGenre.text = genresList.find { it.id == movie.genresIds.first() }?.name
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

        viewModel.getMovieByIdWithActors(movieId)
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
        viewModel.movie.observe(requireActivity(), movieInfoObserver)
        viewModel.actors.observe(requireActivity(), actorsInfoObserver)
        viewModel.loadingDataState.observe(requireActivity(), stateObserver)
        requireView().findViewById<MotionLayout>(R.id.movie_details_root).transitionToEnd()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.movie.removeObserver(movieInfoObserver)
        viewModel.actors.removeObserver(actorsInfoObserver)
        viewModel.loadingDataState.removeObserver(stateObserver)
    }

    // Устанавливаем актерский состав
    private fun setActorsCastRecycleView(cast: List<Actor>) {
        actorsRecyclerView = fragmentView.findViewById(R.id.rv_actors)
        actorsRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        actorsRecyclerView.addItemDecoration(
            ActorsItemDecoration(
                top = resources.getDimensionPixelSize(
                    R.dimen.offSetActorCard
                ),
                left = resources.getDimensionPixelSize(R.dimen.offSetActorCard),
                right = resources.getDimensionPixelSize(R.dimen.offSetActorCard),
                bottom = resources.getDimensionPixelSize(R.dimen.offSetBottomActorCard)
            )
        )
        actorsRecyclerView.adapter = actorsCastAdapter
        actorsCastAdapter.actors.clear()
        actorsCastAdapter.setData(cast)
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