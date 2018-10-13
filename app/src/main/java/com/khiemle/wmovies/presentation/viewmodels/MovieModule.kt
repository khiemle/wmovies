package com.khiemle.wmovies.presentation.viewmodels

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
    fun getMoviesViewModelFactory(retrofit: Retrofit) = MoviesViewModel.Factory(retrofit)
}