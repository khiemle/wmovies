package com.khiemle.wmovies.data.repositories

import com.khiemle.wmovies.data.models.Movie
import io.reactivex.Observable
import retrofit2.Retrofit

class MovieRepository(retrofit: Retrofit, appDatabase: AppDatabase? = null) {

    companion object {
        const val PAGE_SIZE = 10
    }

    private val movieRemoteDataSource = MovieRemoteDataSource(retrofit)
    private val movieLocalDataSource = MovieLocalDataSource(appDatabase)

    fun getMovies(page: Int, moviesListType: MoviesListType) : Observable<List<Movie>> {
//        return movieLocalDataSource.getMovies(page, moviesListType)
        return movieRemoteDataSource.getMovies(page, moviesListType).flatMap {
            return@flatMap Observable.just(it.flatMap {
                it.type = if (moviesListType == MoviesListType.NOW_PLAYING) Movie.NOW_PLAYING else Movie.TOP_RATED
                movieLocalDataSource.insertAll(listOf(it))
                listOf(it)
            })
        }
    }

    fun getMovieDetails(id: Long) : Observable<Movie> {
        return movieRemoteDataSource.getMovie(id)
    }

    fun getMovieCredits(id: Long): Observable<Credits> {
        return movieRemoteDataSource.getCredits(id)
    }

}