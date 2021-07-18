package ru.skillbranch.searchmovie.presentation.recycler_views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.presentation.recycler_views.view_holders.EmptyMoviesListViewHolder
import ru.skillbranch.searchmovie.presentation.recycler_views.view_holders.MoviesViewHolder

class MoviesRecyclerAdapter(private val callbackFunction: (title: String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var movies: List<MovieDto>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EMPTY -> EmptyMoviesListViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_empty_movies_list, parent, false)
            )
            TYPE_MOVIE -> MoviesViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_movie, parent, false)
            )
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MoviesViewHolder -> {
                holder.bind(movies[position])
                holder.itemView.setOnClickListener { callbackFunction(movies[position].title) }
            }
            is EmptyMoviesListViewHolder -> {
                holder.bind()
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (movies.isEmpty()) {
            true -> TYPE_EMPTY
            else -> TYPE_MOVIE
        }
    }

    override fun getItemCount(): Int {
        return when(movies.isEmpty()) {
            true -> 1
            else -> movies.size
        }
    }
}

const val TYPE_EMPTY = 0
const val TYPE_MOVIE = 1