package ru.skillbranch.searchmovie.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.dto.CategoryDto
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.data.sources.categories.CategoriesDataSourceImpl
import ru.skillbranch.searchmovie.data.sources.movies.MoviesDataSourceImpl
import ru.skillbranch.searchmovie.presentation.recycler_views.CategoriesCallback
import ru.skillbranch.searchmovie.presentation.recycler_views.MoviesCallback
import ru.skillbranch.searchmovie.presentation.recycler_views.adapters.CategoriesRecyclerAdapter
import ru.skillbranch.searchmovie.presentation.recycler_views.adapters.MoviesRecyclerAdapter
import ru.skillbranch.searchmovie.presentation.recycler_views.decorations.BottomSpaceItemDecoration
import ru.skillbranch.searchmovie.presentation.recycler_views.decorations.RightSpaceItemDecoration
import ru.skillbranch.searchmovie.data.repository.CategoriesModel
import ru.skillbranch.searchmovie.data.repository.MoviesModel

class MainActivity : AppCompatActivity() {
    private lateinit var moviesModel: MoviesModel
    private lateinit var categoriesModel: CategoriesModel
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var moviesRecyclerView: RecyclerView
    private var categories = listOf<CategoryDto>()
    private var movies = listOf<MovieDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        initDataSource()
        setupRecyclerViews()
    }

    private fun setupRecyclerViews() {
        lateinit var categoriesAdapter: CategoriesRecyclerAdapter
        lateinit var moviesAdapter: MoviesRecyclerAdapter

        categoriesRecyclerView = findViewById(R.id.rv_categories)
        moviesRecyclerView = findViewById(R.id.rv_movies)

        val callbackForToast: (String) -> Unit = { showToast(it) }

        // Прокидываем адаптеры
        categoriesAdapter = CategoriesRecyclerAdapter(callbackForToast)
        moviesAdapter = MoviesRecyclerAdapter(callbackForToast)
        categoriesRecyclerView.adapter = categoriesAdapter
        moviesRecyclerView.adapter = moviesAdapter

        // Настраиваем LayoutManager's
        categoriesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val itemWidth = resources.getDimension(R.dimen.item_movie_width).toInt()
        val itemMargin = resources.getDimension(R.dimen.item_movie_end_margin).toInt()
        val moviesRecyclerViewItemWidth = itemWidth + itemMargin
        val moviesRecyclerViewColumnsCount: Int = resources.displayMetrics.widthPixels / moviesRecyclerViewItemWidth
        moviesRecyclerView.layoutManager = GridLayoutManager(this, moviesRecyclerViewColumnsCount)

        // Накидываем декораторы
        val rightSpace = resources.getDimension(R.dimen.item_movie_category_end_margin).toInt()
        val rightSpaceItemDecoration = RightSpaceItemDecoration(rightSpace)
        categoriesRecyclerView.addItemDecoration(rightSpaceItemDecoration)

        val bottomSpace = resources.getDimension(R.dimen.item_movie_bottom_margin).toInt()
        val bottomSpaceItemDecoration = BottomSpaceItemDecoration(bottomSpace)
        moviesRecyclerView.addItemDecoration(bottomSpaceItemDecoration)

        // DiffUtil
        val categoriesCallback = CategoriesCallback(categories, categoriesModel.getCategories())
        val categoriesDiff = DiffUtil.calculateDiff(categoriesCallback)
        categoriesDiff.dispatchUpdatesTo(categoriesAdapter)
        val categories = categoriesModel.getCategories()
        categoriesAdapter.categories = categories

        val moviesCallback = MoviesCallback(movies, moviesModel.getMovies())
        val moviesDiff = DiffUtil.calculateDiff(moviesCallback)
        moviesDiff.dispatchUpdatesTo(moviesAdapter)
        movies = moviesModel.getMovies()
        moviesAdapter.movies = movies
    }

    private fun showToast(message: String?) {
        when {
            message.isNullOrEmpty() -> {
                showToast(getString(R.string.main_empty_message))
            }
            else -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initDataSource() {
        moviesModel = MoviesModel(MoviesDataSourceImpl())
        categoriesModel = CategoriesModel(CategoriesDataSourceImpl())
    }

    private fun updateData() {

    }
}