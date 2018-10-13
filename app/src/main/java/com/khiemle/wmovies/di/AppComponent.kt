package com.khiemle.wmovies.di

import com.khiemle.wmovies.WMoviesApplication
import com.khiemle.wmovies.presentation.viewmodels.MovieModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    NetModule::class,
    AppModule::class,
    ActivitiesModule::class,
    FragmentsModule::class,
    MovieModule::class])
interface AppComponent : AndroidInjector<WMoviesApplication>