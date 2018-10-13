package com.khiemle.wmovies.data.models

import com.google.gson.annotations.SerializedName

data class Cast(
        @SerializedName("cast_id")
        val castId: Long? = null,
        val character: String? = null,
        @SerializedName("credit_id")
        val creditId: String? = null,
        val gender: Int? = null,
        val id: Long? = null,
        val name: String? = null,
        val order: Int? = null,
        @SerializedName("profile_path")
        val profilePath: String? = null
)