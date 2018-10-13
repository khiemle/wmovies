package com.khiemle.wmovies.data.repositories

import com.khiemle.wmovies.data.models.Cast
import com.khiemle.wmovies.data.models.Crew
import com.khiemle.wmovies.data.models.Movie
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("page") page: Int, @Query("api_key") apiKey: String): Observable<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int, @Query("api_key") apiKey: String): Observable<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Long, @Query("api_key") apiKey: String):Observable<Movie>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(@Path("movie_id") id: Long, @Query("api_key") apiKey: String):Observable<Credits>

}

data class MoviesResponse(val results: List<Movie>)

data class Credits(val id: Long?, val cast: List<Cast>?=null, val crew: List<Crew>?=null)


class MovieRemoteDataSource(retrofit: Retrofit) : MovieDataSource() {


    companion object {
        const val API_KEY = "b05355231018268e4d4a052361d655bd"
    }

    private val movieAPI: MovieAPI = retrofit.create(MovieAPI::class.java)

    override fun getMovies(page: Int, moviesListType: MoviesListType): Observable<List<Movie>> {
        return when (moviesListType) {
            MoviesListType.NOW_PLAYING -> movieAPI.getNowPlayingMovies(page, API_KEY).flatMap {
                return@flatMap Observable.just(it.results)
            }
            MoviesListType.TOP_RATED ->  movieAPI.getTopRatedMovies(page, API_KEY).flatMap {
                return@flatMap Observable.just(it.results)
            }
        }
    }

    override fun getMovie(id: Long): Observable<Movie> {
        return movieAPI.getMovieDetails(id, API_KEY)
    }

    override fun getCredits(id: Long): Observable<Credits> {
        return movieAPI.getMovieCredits(id, API_KEY)
    }
}