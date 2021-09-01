package ru.skillbranch.searchmovie.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.LandingAnimator
import ru.skillbranch.searchmovie.App
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.utils.genresList
import ru.skillbranch.searchmovie.presentation.fragments.listeners.CategoriesListener
import ru.skillbranch.searchmovie.presentation.fragments.listeners.MovieClickListener
import ru.skillbranch.searchmovie.presentation.recycler_views.adapters.CategoriesRecyclerAdapter
import ru.skillbranch.searchmovie.presentation.recycler_views.adapters.MoviesRecyclerAdapter
import ru.skillbranch.searchmovie.presentation.recycler_views.decorations.BottomSpaceItemDecoration
import ru.skillbranch.searchmovie.presentation.recycler_views.decorations.ItemMovieOffsetDecoration
import ru.skillbranch.searchmovie.presentation.recycler_views.decorations.RightSpaceItemDecoration
import ru.skillbranch.searchmovie.presentation.view_models.MoviesViewModel

class MoviesFragment : Fragment(), MovieClickListener, CategoriesListener {
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var moviesRecyclerView: RecyclerView
    private var pullToRefreshLayout: SwipeRefreshLayout? = null
    private lateinit var viewModel: MoviesViewModel
    private val categoriesAdapter =
        CategoriesRecyclerAdapter(this)
    private val moviesAdapter = MoviesRecyclerAdapter(this)
    private lateinit var navController: NavController

    /// Observers
    private val moviesObserver = Observer { items: List<Movie> ->
        moviesAdapter.setData(items)
        pullToRefreshLayout?.isRefreshing = false
    }


    private val loadingStateObserver = Observer { state: MoviesViewModel.LoadingDataState ->
        when (state) {
            MoviesViewModel.LoadingDataState.ERROR -> {
            }
            MoviesViewModel.LoadingDataState.UNKNOWN -> {
            }
            MoviesViewModel.LoadingDataState.FINISHED -> {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
        viewModel.moviesList.observe(requireActivity(), moviesObserver)
        viewModel.loadingDataState.observe(requireActivity(), loadingStateObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postponeEnterTransition()
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        pullToRefreshLayout = view.findViewById(R.id.movies_refresh_layout)
        pullToRefreshLayout?.setOnRefreshListener {
            if (App.isNetworkActive) {
                viewModel.handleRefreshMoviesFromApi()
            } else {
                pullToRefreshLayout?.isRefreshing = false
                Snackbar.make(
                    requireActivity().findViewById(R.id.container),
                    getString(R.string.error_internet_connection),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        initRecyclersGenreAndMovies(view)
    }

    private fun initRecyclersGenreAndMovies(view: View) {
        categoriesRecyclerView = view.findViewById(R.id.rv_categories)
        moviesRecyclerView = view.findViewById(R.id.rv_movies)
        // Прокидываем адаптеры
        categoriesRecyclerView.adapter = categoriesAdapter
        categoriesAdapter.setData(genresList)
        moviesRecyclerView.adapter = moviesAdapter
        moviesRecyclerView.itemAnimator = LandingAnimator().apply {
            addDuration = 1000
            removeDuration = 100
            moveDuration = 1000
            changeDuration = 100
        }

        // Настраиваем LayoutManager's
        categoriesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val itemWidth = resources.getDimension(R.dimen.item_movie_width).toInt()
        val itemMargin = resources.getDimension(R.dimen.item_movie_end_margin).toInt()
        val moviesRecyclerViewItemWidth = itemWidth + itemMargin
        val moviesRecyclerViewColumnsCount: Int =
            resources.displayMetrics.widthPixels / moviesRecyclerViewItemWidth
        moviesRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), moviesRecyclerViewColumnsCount)

        // Накидываем декораторы
        val rightSpace = resources.getDimension(R.dimen.item_movie_category_end_margin).toInt()
        val rightSpaceItemDecoration = RightSpaceItemDecoration(rightSpace)
        categoriesRecyclerView.addItemDecoration(rightSpaceItemDecoration)

        val itemDecoration = ItemMovieOffsetDecoration(
            moviesRecyclerViewColumnsCount,
            this.resources.getDimensionPixelSize(R.dimen.item_movie_width)
        )
        val bottomSpace = resources.getDimension(R.dimen.item_movie_bottom_margin).toInt()
        val bottomSpaceItemDecoration = BottomSpaceItemDecoration(bottomSpace)
        moviesRecyclerView.addItemDecoration(itemDecoration)
        moviesRecyclerView.addItemDecoration(bottomSpaceItemDecoration)

        moviesRecyclerView.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoviesFragment()
    }

    override fun onCategoryClick(genreName: String) {
        Toast.makeText(requireContext(), genreName, Toast.LENGTH_SHORT).show()
    }

    override fun onMovieClick(movieId: Int) {
        val bundle = Bundle()
        bundle.putInt(MovieDetailsFragment.MOVIE_ID, movieId)
        navController.navigate(
            R.id.action_nav_movie_fragment_to_nav_movies_details_fragment,
            bundle
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.moviesList.removeObserver(moviesObserver)
        viewModel.loadingDataState.removeObserver(loadingStateObserver)
    }
}