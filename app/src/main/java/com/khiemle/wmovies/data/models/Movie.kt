package com.khiemle.wmovies.data.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Movie (
        var adult: Boolean? = null,
        @SerializedName("backdrop_path") var backdropPath: String? = null,
        @Ignore @SerializedName("genre_ids") val genreIds: List<Long>? = null,
        @PrimaryKey var id: Long = 0,
        @SerializedName("original_language") var originalLanguage: String? = null,
        @SerializedName("original_title") var originalTitle: String? = null,
        var overview: String? = null,
        var popularity: Double? = null,
        @SerializedName("poster_path") var posterPath: String? = null,
        @SerializedName("release_date") var releaseDate: String? = null,
        var title: String? = null,
        var video: Boolean? = null,
        @SerializedName("vote_average") var voteAverage: Double? = null,
        @SerializedName("vote_count") var voteCount: Long? = null,
        @Ignore var genres: List<Genre>? = null,
        var runtime: Int?= null,
        var type: Int = 0
) {
    companion object {
        const val NOW_PLAYING = 0
        const val TOP_RATED = 1
    }

    constructor():this(null, null, null,0, null, null, null, null, null, null, null,null,null,null, null, null, 0)
}