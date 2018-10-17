package com.khiemle.wmovies.data.models

import com.google.gson.annotations.SerializedName

data class Cast(
        @SerializedName("cast_id")
        val castId: Long? = null,
        val character: String? = null,
        @SerializedName("credit_id")
        val creditId: String? = null,
        val gender: Int? = null,
        override val id: Long? = null,
        override val name: String? = null,
        val order: Int? = null,
        @SerializedName("profile_path")
        override val profilePath: String? = null
) : Contributor(id, name, character, profilePath)