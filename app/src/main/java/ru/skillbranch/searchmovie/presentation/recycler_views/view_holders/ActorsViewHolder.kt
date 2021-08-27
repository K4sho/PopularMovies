package ru.skillbranch.searchmovie.presentation.recycler_views.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.database.entities.Actor

class ActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(model: Actor) {
        itemView.findViewById<TextView>(R.id.iv_card_actor_name).text = model.actorName
        // Если из API не пришла фотка, то мы ставим placeholder
        if (model.photo.isEmpty()) {
            Glide
                .with(itemView.context)
                .load(R.drawable.placeholder_actor)
                .transform(RoundedCorners(10)) // У нас фотки хоть и сглаженны из фигмы, но мало ли с API придут не закругленные
                .into(itemView.findViewById<ImageView>(R.id.iv_card_actor_photo))
        } else {
            Glide
                .with(itemView.context)
                .load(model.photo)
                .transform(RoundedCorners(10)) // У нас фотки хоть и сглаженны из фигмы, но мало ли с API придут не закругленные
                .into(itemView.findViewById<ImageView>(R.id.iv_card_actor_photo))
        }
    }
}