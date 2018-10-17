package com.khiemle.wmovies.data.models

import com.google.gson.annotations.SerializedName

open class Contributor(
        open val id: Long? = null,
        open val name: String? = null,
        val description: String? = null,
        @SerializedName("profile_path")
        open val profilePath: String? = null
)