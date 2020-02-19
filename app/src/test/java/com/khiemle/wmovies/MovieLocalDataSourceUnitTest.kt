package com.khiemle.wmovies

import com.khiemle.wmovies.data.models.Movie
import com.khiemle.wmovies.data.repositories.AppDatabase
import com.khiemle.wmovies.data.repositories.MovieDAO
import com.khiemle.wmovies.data.repositories.MovieLocalDataSource
import com.khiemle.wmovies.data.repositories.MoviesListType
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class MovieLocalDataSourceUnitTest {

    private lateinit var movie: Movie
    private lateinit var nowPlayingMovies: MutableList<Movie>
    private lateinit var topRatedMovies: MutableList<Movie>

    @Before
    fun setup() {
        movie = Movie(id = 1L, title = "Joker")
        nowPlayingMovies = mutableListOf()
        topRatedMovies = mutableListOf()

        for (i in 0..10) {
            nowPlayingMovies.add(Movie(id = i.toLong(), title = "Movie$i", type = Movie.NOW_PLAYING))
        }
        for (i in 0..10) {
            topRatedMovies.add(Movie(id = i.toLong(), title = "Movie$i", type = Movie.TOP_RATED))
        }
    }

    @Test
    fun whenGetMovieWithID_shouldReturnRightMovie() {
        val appDatabase = mock<AppDatabase>()
        val movieDAO = mock<MovieDAO>()

        whenever(movieDAO.findById(1L)).thenReturn(Observable.just(movie))
        whenever(appDatabase.movieDAO()).thenReturn(movieDAO)

        val movieLocalDataSource = MovieLocalDataSource(appDatabase)

        val result: TestObserver<Movie> = movieLocalDataSource.getMovie(1L).test()

        result.assertValue(movie)
        result.dispose()
    }

    @Test
    fun whenGetNowPlayingMoviesWithPage_shouldReturnRightMovies() {
        val appDatabase = mock<AppDatabase>()
        val movieDAO = mock<MovieDAO>()


        whenever(movieDAO.getObservableNowPlayingMovies(Movie.NOW_PLAYING)).thenReturn(
                Observable.just(nowPlayingMovies)
        )
        whenever(movieDAO.getObservableNowPlayingMovies(Movie.TOP_RATED)).thenReturn(
                Observable.just(topRatedMovies)
        )

        whenever(appDatabase.movieDAO()).thenReturn(movieDAO)

        val movieLocalDataSource = MovieLocalDataSource(appDatabase)

        val result: TestObserver<List<Movie>> = movieLocalDataSource.getMovies(1, MoviesListType.NOW_PLAYING).test()

        result.assertValue(nowPlayingMovies)
        result.dispose()

    }

    @Test
    fun whenGetTopRatedMoviesWithPage_shouldReturnRightMovies() {
        val appDatabase = mock<AppDatabase>()
        val movieDAO = mock<MovieDAO>()


        whenever(movieDAO.getObservableNowPlayingMovies(Movie.NOW_PLAYING)).thenReturn(
                Observable.just(nowPlayingMovies)
        )
        whenever(movieDAO.getObservableTopRatedMovies(Movie.TOP_RATED)).thenReturn(
                Observable.just(topRatedMovies)
        )

        whenever(appDatabase.movieDAO()).thenReturn(movieDAO)

        val movieLocalDataSource = MovieLocalDataSource(appDatabase)

        val result: TestObserver<List<Movie>> = movieLocalDataSource.getMovies(1, MoviesListType.TOP_RATED).test()

        result.assertValue(topRatedMovies)
        result.dispose()

    }


}