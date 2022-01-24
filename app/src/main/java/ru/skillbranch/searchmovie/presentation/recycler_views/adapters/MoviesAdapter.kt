package ru.skillbranch.searchmovie.presentation.recycler_views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.presentation.fragments.listeners.MovieClickListener
import ru.skillbranch.searchmovie.presentation.recycler_views.view_holders.EmptyMoviesListViewHolder
import ru.skillbranch.searchmovie.presentation.recycler_views.view_holders.MoviesViewHolder
import java.util.*
import kotlin.collections.ArrayList

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title == newItem.title
    }
}


class MoviesRecyclerAdapter(
    private val listener: MovieClickListener
) :
    ListAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK), Filterable {

    private var movies: List<Movie> = emptyList()
    // For filter list
    var moviesListAll = ArrayList<Movie>(movies)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EMPTY -> EmptyMoviesListViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_empty_movies_list, parent, false)
            )
            TYPE_MOVIE -> MoviesViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_movie, parent, false), listener
            )
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MoviesViewHolder -> {
                holder.bind(movies[position])
            }
            is EmptyMoviesListViewHolder -> {
                holder.bind()
            }
        }

    }

    fun setData(newMovies: List<Movie>) {
        movies = newMovies
        submitList(movies)
    }

    override fun getItemViewType(position: Int): Int {
        return when (movies.isEmpty()) {
            true -> TYPE_EMPTY
            else -> TYPE_MOVIE
        }
    }

    override fun getItemCount(): Int {
        return when (movies.isEmpty()) {
            true -> 1
            else -> movies.size
        }
    }

    companion object {
        const val TYPE_EMPTY = 0
        const val TYPE_MOVIE = 1
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredMovies = ArrayList<Movie>()
                if (constraint == null || constraint.isEmpty()) {
                    filteredMovies.addAll(moviesListAll)
                } else {
                    val filterPattern =
                        constraint.toString().toLowerCase(Locale.getDefault()).trim()
                    filteredMovies.addAll(moviesListAll.filter {
                        it.title.toLowerCase(Locale.getDefault()).contains(filterPattern)
                    })
                }
                val results = FilterResults()
                results.values = filteredMovies
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                movies = emptyList()
                setData(results?.values as List<Movie>)
            }

        }
    }
}