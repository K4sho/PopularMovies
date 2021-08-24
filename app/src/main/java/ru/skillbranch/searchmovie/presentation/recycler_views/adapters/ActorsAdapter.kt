package ru.skillbranch.searchmovie.presentation.recycler_views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.database.entities.Actor
import ru.skillbranch.searchmovie.presentation.recycler_views.ActorsCallback
import ru.skillbranch.searchmovie.presentation.recycler_views.view_holders.ActorsViewHolder

class ActorsCastAdapter : RecyclerView.Adapter<ActorsViewHolder>() {

    var actors: MutableList<Actor> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_actor, parent, false)
        return ActorsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        holder.bind(actors[position])
    }

    override fun getItemCount(): Int {
        return actors.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(initCategories: List<Actor>) {
        // DiffUtil
        val actorsCallback = ActorsCallback(
            actors,
            initCategories
        )
        val categoriesDiff = DiffUtil.calculateDiff(actorsCallback)
        actors = initCategories as MutableList<Actor>
        categoriesDiff.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
}