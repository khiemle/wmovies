package com.khiemle.wmovies.data.models

import com.google.gson.annotations.SerializedName

class Crew: Contributor() {
    @SerializedName("credit_id")
    val creditId: String? = null
    val department: String? = null
    val gender: Long? = null
    var job: String? = null
    override fun description() = job
}