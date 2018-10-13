package com.khiemle.wmovies.presentation.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.khiemle.wmovies.R
import com.khiemle.wmovies.databinding.FragmentMovieDetailsBinding
import com.khiemle.wmovies.presentation.HomeActivity
import com.khiemle.wmovies.presentation.viewmodels.MovieViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class MovieDetailsFragment: Fragment() {
    companion object {
        const val ARG_MOVIE_ID = "ARG_MOVIE_ID"
    }
    private lateinit var binding: FragmentMovieDetailsBinding
    @Inject lateinit var glide: RequestManager
    @Inject lateinit var movieViewModelFactory: MovieViewModel.Factory

    private val movieViewModel by lazy(mode = LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, movieViewModelFactory).get(MovieViewModel::class.java)
    }

    //
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)

        arguments?.getLong(ARG_MOVIE_ID)?.let {
            movieViewModel.getMovie(it)
            movieViewModel.movie.observe(this, Observer {
                it?.let {
                    binding.movieViewModel = movieViewModel
                    binding.executePendingBindings()
                    binding.progressBar.visibility = View.GONE
                    movieViewModel.getImageCoverUrl()?.let {
                        glide?.load(it)?.into(binding.imgCover)
                    }
                }
            })
        }

        return binding.root
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (activity is HomeActivity) {
            (activity as HomeActivity).showBottomNavigation(false)
        }
    }
}