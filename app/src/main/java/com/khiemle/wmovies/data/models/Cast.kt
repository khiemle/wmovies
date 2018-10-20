package com.khiemle.wmovies.data.models

import com.google.gson.annotations.SerializedName

class Cast : Contributor() {
    @SerializedName("cast_id")
    val castId: Long? = null
    var character: String? = null
    @SerializedName("credit_id")
    val creditId: String? = null
    val gender: Int? = null
    val order: Int? = null
    override fun description() = character
}