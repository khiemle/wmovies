package com.khiemle.wmovies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.khiemle.wmovies.data.models.Contributor
import com.khiemle.wmovies.databinding.RvContributorItemBinding

class CastsCrewsAdapter(private val glide : RequestManager?) : ListAdapter<Contributor, CastsCrewsAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<Contributor>() {
            override fun areItemsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
                return oldItem == newItem
            }
        })
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvContributorItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, glide)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    class ViewHolder(private var binding: RvContributorItemBinding, private var glide: RequestManager?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contributor: Contributor) {
            binding.contributor = contributor
            binding.executePendingBindings()
            val imageUrl = "https://image.tmdb.org/t/p/w185${contributor.profilePath}"
            glide?.load(imageUrl)?.apply(RequestOptions.circleCropTransform())?.into(binding.imgContributor)
        }
    }
}