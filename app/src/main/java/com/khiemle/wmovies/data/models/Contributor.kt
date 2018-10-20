package com.khiemle.wmovies.data.models

import com.google.gson.annotations.SerializedName

open class Contributor {
    var id: Long? = null
    var name: String? = null
    open var description: String? = null
        get() = description()
    @SerializedName("profile_path")
    var profilePath: String? = null

    open fun description() = description
}