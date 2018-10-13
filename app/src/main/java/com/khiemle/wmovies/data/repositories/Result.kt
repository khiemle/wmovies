package com.khiemle.wmovies.data.repositories

enum class RequestStatus {
    SUCCESSFUL, ERROR, LOADING, IDLE
}

class Result(val status: RequestStatus, val errorMessage: String? = null)