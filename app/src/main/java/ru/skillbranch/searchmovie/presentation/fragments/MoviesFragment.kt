package ru.skillbranch.searchmovie.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.*
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.data.repository.CategoriesRepository
import ru.skillbranch.searchmovie.data.repository.MoviesRepository
import ru.skillbranch.searchmovie.data.sources.categories.CategoriesDataSourceImpl
import ru.skillbranch.searchmovie.data.sources.movies.MoviesDataSourceImpl
import ru.skillbranch.searchmovie.presentation.fragments.listeners.CategoriesListener
import ru.skillbranch.searchmovie.presentation.fragments.listeners.MovieClickListener
import ru.skillbranch.searchmovie.presentation.recycler_views.CategoriesCallback
import ru.skillbranch.searchmovie.presentation.recycler_views.MoviesCallback
import ru.skillbranch.searchmovie.presentation.recycler_views.adapters.CategoriesRecyclerAdapter
import ru.skillbranch.searchmovie.presentation.recycler_views.adapters.MoviesRecyclerAdapter
import ru.skillbranch.searchmovie.presentation.recycler_views.decorations.BottomSpaceItemDecoration
import ru.skillbranch.searchmovie.presentation.recycler_views.decorations.ItemMovieOffsetDecoration
import ru.skillbranch.searchmovie.presentation.recycler_views.decorations.RightSpaceItemDecoration
import ru.skillbranch.searchmovie.presentation.recycler_views.view_holders.CategoriesViewHolder

class MoviesFragment : Fragment(), MovieClickListener, CategoriesListener {
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var pullToRefreshLayout: SwipeRefreshLayout
    private val moviesRepository = MoviesRepository(MoviesDataSourceImpl())
    private val categoriesRepository = CategoriesRepository(CategoriesDataSourceImpl())
    private val categoriesAdapter =
        CategoriesRecyclerAdapter(this, categoriesRepository.getCategories())
    private val moviesAdapter = MoviesRecyclerAdapter(this, moviesRepository.getMovies())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pullToRefreshLayout = view.findViewById(R.id.movies_refresh_layout)
        pullToRefreshLayout.setOnRefreshListener {
            setDataForMovieAdapter()
            pullToRefreshLayout.isRefreshing = false
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
            this?.resources?.getDimensionPixelSize(R.dimen.item_movie_width) ?: 150
        )
        val bottomSpace = resources.getDimension(R.dimen.item_movie_bottom_margin).toInt()
        val bottomSpaceItemDecoration = BottomSpaceItemDecoration(bottomSpace)
        moviesRecyclerView.addItemDecoration(itemDecoration)
        moviesRecyclerView.addItemDecoration(bottomSpaceItemDecoration)

        // DiffUtil
        val categoriesCallback = CategoriesCallback(
            categoriesRepository.getCategories(),
            categoriesRepository.getCategories()
        )
        val categoriesDiff = DiffUtil.calculateDiff(categoriesCallback)
        categoriesDiff.dispatchUpdatesTo(categoriesRecyclerView.adapter as RecyclerView.Adapter<CategoriesViewHolder>)
        categoriesRepository.getCategories()

        val moviesCallback =
            MoviesCallback(moviesRepository.getMovies(), moviesRepository.getMovies())
        val moviesDiff = DiffUtil.calculateDiff(moviesCallback)
        moviesDiff.dispatchUpdatesTo(moviesRecyclerView.adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoviesFragment()
    }

    override fun onCategoryClick(genreName: String) {
        Toast.makeText(requireContext(), genreName, Toast.LENGTH_SHORT).show()
    }

    override fun onMovieClick(movie: MovieDto) {
        parentFragmentManager.beginTransaction().replace(
            R.id.main_fragment_container,
            MovieDetailsFragment.newInstance(movie)
        ).addToBackStack(null).commit()
    }

    private fun setDataForMovieAdapter() = runBlocking {
        setData()
    }

    private suspend fun setData() = coroutineScope {
        val download: Job = launch {
            try {
                val movieList: List<MovieDto> = moviesRepository.getMovies().shuffled()
                delay(400L)
                moviesAdapter.setData(movieList)
            } catch (e: CancellationException) {
                Log.e("Coroutine", "Catch ERROR")
                val movieList: List<MovieDto> = emptyList()
                Toast.makeText(requireContext(), "Нет фильмов для отображения", Toast.LENGTH_LONG).show()
                moviesAdapter.setData(movieList)
            }
        }
        download.join()
    }
}
