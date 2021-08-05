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
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.dto.CategoryDto
import ru.skillbranch.searchmovie.data.dto.MovieDto
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

    private val moviesObserver = Observer { items: List<MovieDto> ->
        moviesAdapter.setData(items)
        pullToRefreshLayout?.isRefreshing = false
    }

    private val categoriesObserver = Observer { items: List<CategoryDto> ->
        categoriesAdapter.setData(items)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
        viewModel.moviesList.observe(requireActivity(), moviesObserver)
        viewModel.categoriesList.observe(requireActivity(), categoriesObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        pullToRefreshLayout = view.findViewById(R.id.movies_refresh_layout)
        pullToRefreshLayout?.setOnRefreshListener {
            viewModel.handleRefreshMovies()
        }
        initRecyclersGenreAndMovies(view)
    }

    private fun initRecyclersGenreAndMovies(view: View) {
        categoriesRecyclerView = view.findViewById(R.id.rv_categories)
        moviesRecyclerView = view.findViewById(R.id.rv_movies)

        // Прокидываем адаптеры
        categoriesRecyclerView.adapter = categoriesAdapter
        moviesRecyclerView.adapter = moviesAdapter

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
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoviesFragment()
    }

    override fun onCategoryClick(genreName: String) {
        Toast.makeText(requireContext(), genreName, Toast.LENGTH_SHORT).show()
    }

    override fun onMovieClick(movie: MovieDto) {
        val bundle = Bundle()
        bundle.putString(MovieDetailsFragment.MOVIE_NAME, movie.title)
        bundle.putString(MovieDetailsFragment.MOVIE_DESCRIPTION, movie.description)
        bundle.putInt(MovieDetailsFragment.MOVIE_RATE_SCORE, movie.rateScore)
        bundle.putInt(MovieDetailsFragment.MOVIE_AGE, movie.ageLimit)
        bundle.putString(MovieDetailsFragment.MOVIE_IMAGE_URL, movie.imageUrl)
        bundle.putString(MovieDetailsFragment.ACTOR_IMAGE_URL_1, movie.actors[0].imageUrl)
        bundle.putString(MovieDetailsFragment.ACTOR_IMAGE_URL_2, movie.actors[1].imageUrl)
        bundle.putString(MovieDetailsFragment.ACTOR_IMAGE_URL_3, movie.actors[2].imageUrl)
        bundle.putString(MovieDetailsFragment.ACTOR_NAME_1, movie.actors[0].name)
        bundle.putString(MovieDetailsFragment.ACTOR_NAME_2, movie.actors[1].name)
        bundle.putString(MovieDetailsFragment.ACTOR_NAME_3, movie.actors[2].name)
        navController.navigate(
            R.id.action_nav_movie_fragment_to_nav_movies_details_fragment,
            bundle
        )
    }
}
