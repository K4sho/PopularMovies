package ru.skillbranch.searchmovie.presentation.recycler_views.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.dto.MovieDto

class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val movieCoverImageView: ImageView =
        itemView.findViewById(R.id.iv_item_movie_cover)
    private val movieTitleTextView: TextView =
        itemView.findViewById(R.id.tv_item_movie_title)
    private val movieDescriptionTextView: TextView =
        itemView.findViewById(R.id.tv_item_movie_desc)
    private val star1ImageView: ImageView = itemView.findViewById(R.id.rating_star_1)
    private val star2ImageView: ImageView = itemView.findViewById(R.id.rating_star_2)
    private val star3ImageView: ImageView = itemView.findViewById(R.id.rating_star_3)
    private val star4ImageView: ImageView = itemView.findViewById(R.id.rating_star_4)
    private val star5ImageView: ImageView = itemView.findViewById(R.id.rating_star_5)
    private val movieAgeRatingTextView: TextView =
        itemView.findViewById(R.id.tv_item_movie_age_limit)

    fun bind(movie: MovieDto) {
        movieCoverImageView.load(movie.imageUrl)
        movieTitleTextView.text = movie.title
        movieDescriptionTextView.text = movie.description
        star1ImageView.setImageResource(if (movie.rateScore > 0) R.drawable.ic_star_selected else R.drawable.ic_star_unselected)
        star2ImageView.setImageResource(if (movie.rateScore > 1) R.drawable.ic_star_selected else R.drawable.ic_star_unselected)
        star3ImageView.setImageResource(if (movie.rateScore > 2) R.drawable.ic_star_selected else R.drawable.ic_star_unselected)
        star4ImageView.setImageResource(if (movie.rateScore > 3) R.drawable.ic_star_selected else R.drawable.ic_star_unselected)
        star5ImageView.setImageResource(if (movie.rateScore > 4) R.drawable.ic_star_selected else R.drawable.ic_star_unselected)
        movieAgeRatingTextView.text = itemView.context.getString(
            R.string.age_restriction,
            movie.ageLimit.toString()
        )
    }
}