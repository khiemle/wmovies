package com.khiemle.wmovies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.khiemle.wmovies.data.models.Movie
import com.khiemle.wmovies.databinding.RvMovieItemBinding
import com.khiemle.wmovies.domains.ConfigurationDomain

class MoviesAdapter(private val glide : RequestManager?,
                                 private var listener: OnItemClickListener,private val configurationDomain: ConfigurationDomain?) : PagedListAdapter<Movie, MoviesAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        })
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvMovieItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, glide, configurationDomain)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position);
        holder.bind(movie, listener)
    }

    class ViewHolder(private var binding: RvMovieItemBinding, private var glide: RequestManager?, private val configurationDomain: ConfigurationDomain?) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie?, listener: OnItemClickListener?) {
            binding.movieModel = movie
            binding.executePendingBindings()
            val imageUrl = "${configurationDomain?.getBaseUrl()}${configurationDomain?.getPosterWidthQuality(4)}${movie?.posterPath}"
            glide?.load(imageUrl)?.into(binding.imgPoster)
            if (listener != null) {
                binding.root.setOnClickListener { _ -> movie?.id?.let { listener.onItemClick(layoutPosition, it) } }
            }

        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, id: Long)
    }
}
