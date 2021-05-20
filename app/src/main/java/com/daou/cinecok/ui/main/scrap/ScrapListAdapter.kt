package com.daou.cinecok.ui.main.scrap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daou.cinecok.data.model.MovieData
import com.daou.cinecok.databinding.ItemScrapMovieBinding


class ScrapListAdapter : ListAdapter<MovieData, ScrapListAdapter.ViewHolder>(diffUtil) {
    private lateinit var onItemClickListener : ScrapListAdapter.OnItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(
            ItemScrapMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        val view = viewHolder.itemView
        view.layoutParams.height = (parent.measuredWidth / 2 * 1.5 ).toInt()

        return viewHolder
    }


    fun setOnItemClickListener(listener: ScrapListAdapter.OnItemClickListener) {
        onItemClickListener = listener
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(private val binding: ItemScrapMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener{
                if (::onItemClickListener.isInitialized)
                    onItemClickListener.onItemClick(adapterPosition)
            }
        }
        fun bind(item: MovieData) {
            binding.favoriteMovie = item

        }
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MovieData>() {
            override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData) = oldItem == newItem
            override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData) = oldItem.compareKey == newItem.compareKey
        }
    }


    interface OnItemClickListener {
        fun onItemClick(itemIndex: Int)
    }
}