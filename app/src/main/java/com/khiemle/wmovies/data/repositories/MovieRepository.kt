package com.khiemle.wmovies.data.repositories

import com.khiemle.wmovies.data.models.Movie
import io.reactivex.Observable
import retrofit2.Retrofit

class MovieRepository(retrofit: Retrofit) {
    private val movieRemoteDataSource = MovieRemoteDataSource(retrofit)

    fun getMovies(page: Int, moviesListType: MoviesListType) : Observable<List<Movie>> {
        return movieRemoteDataSource.getMovies(page, moviesListType)
    }
}