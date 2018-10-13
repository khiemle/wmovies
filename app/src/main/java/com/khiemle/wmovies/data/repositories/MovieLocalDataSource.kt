package com.khiemle.wmovies.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.*
import com.khiemle.wmovies.data.models.Movie
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movies WHERE type LIKE :type ORDER BY popularity DESC")
    fun getAll(type: Int): List<Movie>

    @Query("SELECT * FROM movies WHERE type LIKE :type")
    fun getLiveDataMovies(type: Int): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE type LIKE :type")
    fun getFlowableMovies(type: Int): Flowable<List<Movie>>

    @Query("SELECT * FROM movies WHERE type LIKE :type ORDER BY popularity DESC")
    fun getObservableNowPlayingMovies(type: Int): Observable<List<Movie>>

    @Query("SELECT * FROM movies WHERE type LIKE :type ORDER BY voteAverage DESC, popularity DESC")
    fun getObservableTopRatedMovies(type: Int): Observable<List<Movie>>

    @Query("SELECT * FROM movies WHERE type LIKE :type ORDER BY popularity DESC")
    fun getFactoryNowPlayingMovies(type: Int): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM movies WHERE type LIKE :type ORDER BY voteAverage DESC, popularity DESC")
    fun getFactoryTopRatedMovies(type: Int): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM movies WHERE id LIKE :id LIMIT 1")
    fun findById(id: Long): Observable<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM movies WHERE type LIKE :type")
    fun deleteAll(type: Int)
}

class MovieLocalDataSource(appDatabase: AppDatabase?) : MovieDataSource() {
    private val movieDAO: MovieDAO? = appDatabase?.movieDAO()
    override fun getMovies(page: Int, moviesListType: MoviesListType): Observable<List<Movie>> {
        if (moviesListType == MoviesListType.NOW_PLAYING) {
            movieDAO?.getObservableNowPlayingMovies(Movie.NOW_PLAYING)?.let {
                return it
            }
        } else if (moviesListType == MoviesListType.TOP_RATED) {
            movieDAO?.getObservableTopRatedMovies(Movie.TOP_RATED)?.let {
                return it
            }
        }
        return Observable.just(listOf())
    }

    override fun getMovie(id: Long): Observable<Movie> {
        movieDAO?.findById(id)?.let {
            return it
        }
        return Observable.just(null)
    }

    fun insertAll(movies: List<Movie>) {
        movieDAO?.insertAll(movies)
    }

    fun getMoviesWithPaging(pageSize: Int,moviesListType: MoviesListType, boundaryCallback: PagedList.BoundaryCallback<Movie>) : LiveData<PagedList<Movie>>? {
        val factory: DataSource.Factory<Int, Movie>? = if (moviesListType == MoviesListType.NOW_PLAYING)
            movieDAO?.getFactoryNowPlayingMovies(Movie.NOW_PLAYING) else movieDAO?.getFactoryTopRatedMovies(Movie.TOP_RATED)

        return factory?.let {
            val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(true)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(pageSize)
                    .setPrefetchDistance(3)
                    .build()
            LivePagedListBuilder(it, config).setBoundaryCallback(boundaryCallback).build()
        }
    }

    fun clearAll(type: MoviesListType) {
        if (type == MoviesListType.NOW_PLAYING)
            movieDAO?.deleteAll(Movie.NOW_PLAYING)
        else
            movieDAO?.deleteAll(Movie.TOP_RATED)
    }

}

