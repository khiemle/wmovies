package com.khiemle.wmovies.presentation.screens

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.RequestManager
import com.khiemle.wmovies.databinding.FragmentMoviesBinding
import com.khiemle.wmovies.R
import com.khiemle.wmovies.data.repositories.RequestStatus
import com.khiemle.wmovies.domains.ConfigurationDomain
import com.khiemle.wmovies.presentation.HomeActivity
import com.khiemle.wmovies.presentation.adapters.MoviesAdapter
import com.khiemle.wmovies.presentation.viewmodels.MoviesViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class MoviesFragment: Fragment(), MoviesAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var adapter: MoviesAdapter
    @Inject lateinit var glide: RequestManager
    @Inject lateinit var connectivityManager: ConnectivityManager
    @Inject lateinit var configurationDomain: ConfigurationDomain

    private val moviesViewModel by lazy(mode = LazyThreadSafetyMode.NONE) {
        createViewModel()
    }

    abstract fun createViewModel() : MoviesViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        binding.moviesViewModel = moviesViewModel
        adapter = MoviesAdapter(glide, this, configurationDomain)
        binding.rvMovies.layoutManager= LinearLayoutManager(context)
        binding.rvMovies.adapter = adapter
        binding.rvMovies.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))


        moviesViewModel.movies.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            }
        })


        moviesViewModel.status.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                moviesViewModel.status.get()?.let {
                    if (it.status == RequestStatus.ERROR) {
                        it.errorMessage?.let { it1 -> showError(it1) }
                    }
                    if (it.status != RequestStatus.LOADING) {
                        binding.swipeContainer.isRefreshing = false
                    }
                }
            }

        })

        binding.swipeContainer.setOnRefreshListener(this)

        return binding.root
    }

    fun showError(error: String) {
        activity?.runOnUiThread {
            val dialog = AlertDialog.Builder(activity).setTitle("Alert").setMessage(error).setPositiveButton("Ok") { _, _ -> }.create()
            dialog.show()
        }
    }

    override fun onRefresh() {
        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected) {
            moviesViewModel.clearAndReload()
        } else {
            binding.swipeContainer.isRefreshing = false
        }
    }

    override fun onItemClick(position: Int, id: Long) {
        if (activity is HomeActivity) {
            (activity as HomeActivity).openMovieDetails(id)
        }
    }}