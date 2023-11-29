package com.example.bostatask_1.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bostatask_1.databinding.GridItemImageViewBinding
import com.example.bostatask_1.network.PhotoProperty

class AlbumGridRVAdaptor(private val clickListener: (photoProperty: PhotoProperty) -> Unit) :
    ListAdapter<PhotoProperty, AlbumGridRVAdaptor.PhotosGritViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosGritViewHolder {
        return PhotosGritViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotosGritViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class PhotosGritViewHolder(private val binding: GridItemImageViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): PhotosGritViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GridItemImageViewBinding.inflate(layoutInflater, parent, false)
                return PhotosGritViewHolder(binding)
            }

        }

        fun bind(
            propertyItem: PhotoProperty,
            clickListener: (photoProperty: PhotoProperty) -> Unit
        ) {
            binding.url = propertyItem.thumbnailUrl
            binding.title = propertyItem.title
            binding.gridItemConstraintLayout.setOnClickListener { clickListener(propertyItem) }
            binding.executePendingBindings()
        }
    }

    object DiffCallBack : DiffUtil.ItemCallback<PhotoProperty>() {
        override fun areItemsTheSame(oldItem: PhotoProperty, newItem: PhotoProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PhotoProperty, newItem: PhotoProperty): Boolean {
            return oldItem == newItem
        }
    }
}