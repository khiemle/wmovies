package com.khiemle.wmovies.data.repositories

import com.khiemle.wmovies.data.models.Movie
import io.reactivex.Observable

abstract class MovieDataSource {
    abstract fun getMovies(page: Int): Observable<List<Movie>>
    abstract fun getMovie(id: Long): Observable<Movie>
}