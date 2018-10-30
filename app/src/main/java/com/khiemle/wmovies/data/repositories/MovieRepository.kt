package com.khiemle.wmovies.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.khiemle.wmovies.data.models.Configuration
import com.khiemle.wmovies.data.models.Contributor
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
        return movieRemoteDataSource.getMovies(page, moviesListType).flatMap {
            return@flatMap Observable.just(it.flatMap {
                it.type = if (moviesListType == MoviesListType.NOW_PLAYING) Movie.NOW_PLAYING else Movie.TOP_RATED
                it.page = page
                movieLocalDataSource.insertAll(listOf(it))
                listOf(it)
            })
        }
    }

    fun getMovie(movieId: Long) : Observable<Movie> {
        return movieLocalDataSource.getMovie(movieId)
    }

    fun getMovieDetails(id: Long) : Observable<Movie> {
        return movieRemoteDataSource.getMovie(id)
    }

    fun getMovieCredits(id: Long): Observable<Credits> {
        return movieRemoteDataSource.getCredits(id)
    }

    fun getMovieContributors(id: Long): Observable<List<Contributor>> {
        return movieRemoteDataSource.getCredits(id).flatMap {
            var mutableList = mutableListOf<Contributor>()
            it.cast?.let { list -> mutableList.addAll(list) }
            it.crew?.let { list -> mutableList.addAll(list) }
            return@flatMap Observable.just(mutableList)
        }
    }

    fun getMoviesWithPaging(moviesListType: MoviesListType, boundaryCallback: PagedList.BoundaryCallback<Movie>): LiveData<PagedList<Movie>>? {
        return movieLocalDataSource.getMoviesWithPaging(PAGE_SIZE, moviesListType ,boundaryCallback)
    }


    fun clearLocalDataSource(type: MoviesListType): Observable<Int> {
        return Observable.just(0).flatMap {
            movieLocalDataSource.clearAll(type)
            return@flatMap Observable.just(it)
        }
    }

    fun getConfigurations() : Observable<Configuration> {
        return movieRemoteDataSource.getConfigurations()
    }
}