package ru.skillbranch.searchmovie.presentation.recycler_views.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.presentation.fragments.listeners.MovieClickListener

class MoviesViewHolder(itemView: View, private val movieClickListener: MovieClickListener) :
    RecyclerView.ViewHolder(itemView) {
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
    private val starsRating: List<ImageView> =
        listOf(star1ImageView, star2ImageView, star3ImageView, star4ImageView, star5ImageView)
    private val movieAgeRatingTextView: TextView =
        itemView.findViewById(R.id.tv_item_movie_age_limit)

    fun bind(movie: MovieDto, position: Int) {
        itemView.setOnClickListener { movieClickListener.onMovieClick(position) }
        movieCoverImageView.load(movie.imageUrl)
        movieTitleTextView.text = movie.title
        movieDescriptionTextView.text = movie.description
        starsRating.forEachIndexed { index, star ->
            star.setImageResource(if (movie.rateScore > index) R.drawable.ic_star_selected else R.drawable.ic_star_unselected)
        }
        movieAgeRatingTextView.text = itemView.context.getString(
            R.string.age_restriction,
            movie.ageLimit.toString()
        )
    }
}