package com.khiemle.wmovies.data.repositories

import com.khiemle.wmovies.data.models.Movie
import io.reactivex.Observable

enum class MoviesListType {
    NOW_PLAYING, TOP_RATED
}

abstract class MovieDataSource {
    abstract fun getMovies(page: Int, moviesListType: MoviesListType): Observable<List<Movie>>
    abstract fun getMovie(id: Long): Observable<Movie>
    abstract fun getCredits(id: Long): Observable<Credits>
}