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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.khiemle.wmovies.R
import com.khiemle.wmovies.databinding.FragmentCastsAndCrewsBinding
import com.khiemle.wmovies.presentation.adapters.CastsCrewsAdapter
import com.khiemle.wmovies.presentation.viewmodels.CastsAndCrewsViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CastsAndCrewsFragment: Fragment() {

    companion object {
        const val ARG_MOVIE_ID = "ARG_MOVIE_ID"
    }

    lateinit var binding: FragmentCastsAndCrewsBinding
    @Inject lateinit var factory: CastsAndCrewsViewModel.Factory
    @Inject lateinit var glide: RequestManager
    private val castsAndCrewsViewModel by lazy(mode = LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, factory).get(CastsAndCrewsViewModel::class.java)
    }

    private lateinit var adapter: CastsCrewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_casts_and_crews,container, false)
        binding.castsAndCrewsViewModel = castsAndCrewsViewModel
        adapter = CastsCrewsAdapter(glide, castsAndCrewsViewModel.configurationDomain)
        binding.rvContributors.adapter = adapter
        binding.rvContributors.layoutManager = LinearLayoutManager(context)
        binding.rvContributors.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        arguments?.getLong(ARG_MOVIE_ID)?.let {
            castsAndCrewsViewModel.getMovie(it)
            castsAndCrewsViewModel.getListContributors(it)
            castsAndCrewsViewModel.movie.observe(this, Observer {
                it?.let {
                    binding.tvMovieName.text = it.originalTitle
                }
            })
            castsAndCrewsViewModel.listContributors.observe(this, Observer {
                it?.let {
                    adapter.submitList(it)
                    adapter.notifyDataSetChanged()
                    binding.tvCount.text = "${it.size} ${getString(R.string.people)}"
                }
            })
        }
        return binding.root
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}