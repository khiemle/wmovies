package com.khiemle.wmovies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.khiemle.wmovies.data.models.Cast
import com.khiemle.wmovies.databinding.RvCastItemBinding
import com.khiemle.wmovies.domains.ConfigurationDomain

class CastsAdapter(private val glide : RequestManager?, private val configurationDomain: ConfigurationDomain) : ListAdapter<Cast, CastsAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem == newItem
            }
        })
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvCastItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, glide, configurationDomain)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    class ViewHolder(private var binding: RvCastItemBinding, private var glide: RequestManager?, private val configurationDomain: ConfigurationDomain) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast) {
            binding.cast = cast
            binding.executePendingBindings()
            val imageUrl = "${configurationDomain.getBaseUrl()}${configurationDomain.getProfileWidthQuality(1)}${cast.profilePath}"
            glide?.load(imageUrl)?.apply(RequestOptions.circleCropTransform())?.into(binding.imgCast)
        }
    }
}