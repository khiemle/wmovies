package com.khiemle.wmovies.data.repositories

import com.khiemle.wmovies.data.models.Movie
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {
    @GET("movie/now_playing")
    fun getMovies(@Query("page") page: Int, @Query("api_key") apiKey: String): Observable<MoviesResponse>
}

data class MoviesResponse(val results: List<Movie>)


class MovieRemoteDataSource(retrofit: Retrofit) : MovieDataSource() {


    companion object {
        const val API_KEY = "b05355231018268e4d4a052361d655bd"
    }

    private val movieAPI: MovieAPI = retrofit.create(MovieAPI::class.java)

    override fun getMovies(page: Int): Observable<List<Movie>> {
        return movieAPI.getMovies(page, API_KEY).flatMap {
            return@flatMap Observable.just(it.results)
        }
    }

    override fun getMovie(id: Long): Observable<Movie> {
        return Observable.just(Movie())
    }

}