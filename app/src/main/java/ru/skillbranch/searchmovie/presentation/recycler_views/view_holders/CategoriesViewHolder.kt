package ru.skillbranch.searchmovie.presentation.recycler_views.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.dto.CategoryDto

class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val movieCategoryTextView: TextView =
        itemView.findViewById(R.id.tv_item_movie_category)

    fun bind(category: CategoryDto) {
        movieCategoryTextView.text = category.name
    }
}