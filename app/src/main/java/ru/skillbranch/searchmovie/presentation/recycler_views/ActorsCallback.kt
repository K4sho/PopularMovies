package ru.skillbranch.searchmovie.presentation.recycler_views

import androidx.recyclerview.widget.DiffUtil
import ru.skillbranch.searchmovie.data.database.entities.Actor

class ActorsCallback(private val oldList: List<Actor>, private val newList: List<Actor>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].actorName == newList[newItemPosition].actorName

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}