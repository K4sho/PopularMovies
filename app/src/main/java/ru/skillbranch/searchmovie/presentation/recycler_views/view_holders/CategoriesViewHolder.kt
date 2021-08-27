package ru.skillbranch.searchmovie.presentation.recycler_views.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.database.entities.Genre
import ru.skillbranch.searchmovie.presentation.fragments.listeners.CategoriesListener

class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val movieCategoryTextView: TextView =
        itemView.findViewById(R.id.tv_item_movie_category)

    fun bind(category: Genre, listener: CategoriesListener) {
        movieCategoryTextView.text = category.name
        itemView.setOnClickListener { listener.onCategoryClick(category.name) }
    }
}