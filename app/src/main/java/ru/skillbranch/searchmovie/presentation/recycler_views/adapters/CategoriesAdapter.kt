package ru.skillbranch.searchmovie.presentation.recycler_views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.dto.CategoryDto
import ru.skillbranch.searchmovie.presentation.fragments.listeners.CategoriesListener
import ru.skillbranch.searchmovie.presentation.recycler_views.CategoriesCallback
import ru.skillbranch.searchmovie.presentation.recycler_views.view_holders.CategoriesViewHolder

class CategoriesRecyclerAdapter(private val listener: CategoriesListener) :
    RecyclerView.Adapter<CategoriesViewHolder>() {

    private var categories: List<CategoryDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
        return CategoriesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(categories[position], listener)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(initCategories: List<CategoryDto>) {
        // DiffUtil
        val categoriesCallback = CategoriesCallback(
            categories,
            initCategories
        )
        val categoriesDiff = DiffUtil.calculateDiff(categoriesCallback)
        categories = initCategories
        categoriesDiff.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
}