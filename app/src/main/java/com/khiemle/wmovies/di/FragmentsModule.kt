package com.khiemle.wmovies.di

import com.khiemle.wmovies.presentation.screens.NowPlayingFragment
import com.khiemle.wmovies.presentation.screens.TopRatedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {
    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun nowPlayingFragment() : NowPlayingFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun topRatedFragment() : TopRatedFragment
}