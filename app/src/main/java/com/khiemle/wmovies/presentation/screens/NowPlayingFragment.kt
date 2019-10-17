package com.khiemle.wmovies.presentation.screens

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.khiemle.wmovies.presentation.viewmodels.MoviesViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class NowPlayingFragment: MoviesFragment(){
    @Inject lateinit var moviesModelFactory: MoviesViewModel.NowPlayingFactory
    override fun createViewModel(): MoviesViewModel = ViewModelProviders.of(this, moviesModelFactory).get(MoviesViewModel::class.java)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
}