package com.khiemle.wmovies.presentation.viewmodels

import com.khiemle.wmovies.data.repositories.AppDatabase
import com.khiemle.wmovies.di.AppModule
import com.khiemle.wmovies.di.NetModule
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetModule::class, AppModule::class])
class MovieModule {
    @Provides
    @Singleton
    fun getNowPlayingMoviesViewModelFactory(retrofit: Retrofit, appDatabase: AppDatabase?) = MoviesViewModel.NowPlayingFactory(retrofit, appDatabase)

    @Provides
    @Singleton
    fun getTopRatedMoviesViewModelFactory(retrofit: Retrofit, appDatabase: AppDatabase?) = MoviesViewModel.TopRatedFactory(retrofit, appDatabase)

    @Provides
    @Singleton
    fun getMovieViewModelFactory(retrofit: Retrofit) = MovieViewModel.Factory(retrofit)

}