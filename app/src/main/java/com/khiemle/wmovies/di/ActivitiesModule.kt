package com.khiemle.wmovies.di

import com.khiemle.wmovies.presentation.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun homeActivity(): HomeActivity
}