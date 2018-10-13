package com.khiemle.wmovies.data.models

import com.google.gson.annotations.SerializedName

data class Crew(
        @SerializedName("credit_id")
        val creditId: String? = null,
        val department: String? = null,
        val gender: Long? = null,
        val id: Long? = null,
        val job: String? = null,
        val name: String? = null,
        @SerializedName("profile_path")
        val profilePath: String? = null
)