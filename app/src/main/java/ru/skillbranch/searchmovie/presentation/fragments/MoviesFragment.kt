package ru.skillbranch.searchmovie.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.LandingAnimator
import ru.skillbranch.searchmovie.App
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.utils.genresList
import ru.skillbranch.searchmovie.presentation.fragments.listeners.CategoriesListener
import ru.skillbranch.searchmovie.presentation.fragments.listeners.MovieClickListener
import ru.skillbranch.searchmovie.presentation.recycler_views.adapters.CategoriesRecyclerAdapter
import ru.skillbranch.searchmovie.presentation.recycler_views.adapters.MoviesRecyclerAdapter
import ru.skillbranch.searchmovie.presentation.recycler_views.decorations.BottomSpaceItemDecoration
import ru.skillbranch.searchmovie.presentation.recycler_views.decorations.ItemMovieOffsetDecoration
import ru.skillbranch.searchmovie.presentation.recycler_views.decorations.RightSpaceItemDecoration
import ru.skillbranch.searchmovie.presentation.view_models.MoviesViewModel
import ru.skillbranch.searchmovie.databinding.FragmentMoviesListBinding

class MoviesFragment : Fragment(), MovieClickListener, CategoriesListener {
    private var pullToRefreshLayout: SwipeRefreshLayout? = null
    private val viewModel by viewModels<MoviesViewModel>()
    private lateinit var moviesAdapter: MoviesRecyclerAdapter
    private var firstInitAdapter = true

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        postponeEnterTransition()
        val actionBar = binding.toolbar as Toolbar?
        (activity as? AppCompatActivity)?.setSupportActionBar(actionBar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        pullToRefreshLayout = binding.moviesRefreshLayout
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
        viewModel.loadingDataState.observe(
            viewLifecycleOwner,
            Observer { state: MoviesViewModel.LoadingDataState ->
                when (state) {
                    MoviesViewModel.LoadingDataState.ERROR -> {
                    }
                    MoviesViewModel.LoadingDataState.UNKNOWN -> {
                    }
                    MoviesViewModel.LoadingDataState.FINISHED -> {
                    }
                }
            })
        initRecyclersGenresAndMovies()
    }

    private fun initRecyclersGenresAndMovies() {
        val categoriesRecyclerView = binding.rvCategories
        val moviesRecyclerView = binding.rvMovies
        // Прокидываем адаптеры
        moviesAdapter = MoviesRecyclerAdapter(this)
        val categoriesAdapter = CategoriesRecyclerAdapter(this)
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

        viewModel.moviesList.observe(viewLifecycleOwner, Observer {
            if (firstInitAdapter) {
                moviesAdapter.moviesListAll.clear()
                moviesAdapter.moviesListAll.addAll(it)
                firstInitAdapter = false
            }
            moviesAdapter.setData(it)
            pullToRefreshLayout?.isRefreshing = false
        })
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        searchView = menu.findItem(R.id.action_search)?.actionView as SearchView?
        searchView?.queryHint = getString(R.string.search_movie)

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = false

            override fun onQueryTextChange(query: String): Boolean {
                moviesAdapter.filter.filter(query)
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}