package com.example.moviez.data.models

import androidx.room.Entity
import com.example.moviez.utils.AppConstants
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesModel(

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<MoviesItem?>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)

@Entity(primaryKeys = ["id"])
data class MoviesItem(

	var page: Int,
	var totalPages: Int,

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("original_language")
	val originalLanguage: String? = null,

	@field:SerializedName("original_title")
	val originalTitle: String? = null,

	@field:SerializedName("video")
	val video: Boolean? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("genre_ids")
	val genreIds: List<Int?>? = null,

	@field:SerializedName("poster_path")
	var posterPath: String? = null,

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("popularity")
	val popularity: Any? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("adult")
	val adult: Boolean? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null,

	var favourites: Boolean = false,

	): Serializable {
	fun getFormattedPosterPath(): String? {
		if (posterPath != null && !posterPath!!.startsWith("http")) {
			posterPath = String.format(AppConstants.IMAGE_URL, posterPath)
		}
		return posterPath
	}

	fun isLastPage() : Boolean {
		return page >= totalPages
	}
}
