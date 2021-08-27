package ru.skillbranch.searchmovie.presentation.recycler_views

import androidx.recyclerview.widget.DiffUtil
import ru.skillbranch.searchmovie.data.database.entities.Genre

class CategoriesCallback(
    private val oldList: List<Genre>,
    private val newList: List<Genre>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].name == newList[newItemPosition].name

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}