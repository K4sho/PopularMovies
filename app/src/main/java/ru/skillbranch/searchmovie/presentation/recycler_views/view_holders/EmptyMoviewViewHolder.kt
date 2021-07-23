package ru.skillbranch.searchmovie.presentation.recycler_views.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.searchmovie.R

class EmptyMoviesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val emptyMoviesListTextView: TextView =
        itemView.findViewById(R.id.tv_item_empty_movies_list)

    fun bind() {
        emptyMoviesListTextView.text = itemView.context.getString(R.string.empty_movies_list)
    }
}