package ru.skillbranch.searchmovie.presentation.recycler_views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.dto.CategoryDto
import ru.skillbranch.searchmovie.presentation.recycler_views.view_holders.CategoriesViewHolder

class CategoriesRecyclerAdapter(private val callbackFunction: (title: String) -> Unit) :
    RecyclerView.Adapter<CategoriesViewHolder>() {
    lateinit var categories: List<CategoryDto>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
        return CategoriesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(categories[position])
        holder.itemView.setOnClickListener { callbackFunction(categories[position].name) }
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}