package ru.skillbranch.searchmovie.presentation.recycler_views.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.data.database.entities.Movie
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

    fun bind(movie: Movie) {
        itemView.setOnClickListener { movieClickListener.onMovieClick(movie.movieId) }
        if (movie.imageUrl.isEmpty()) {
            Glide
                .with(itemView.context)
                .load(R.drawable.placeholder_poster)
                .transform(RoundedCorners(10))
                .into(movieCoverImageView)
        } else {
            Glide
                .with(itemView.context)
                .load(movie.imageUrl)
                .transform(RoundedCorners(10))
                .into(movieCoverImageView)
        }
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