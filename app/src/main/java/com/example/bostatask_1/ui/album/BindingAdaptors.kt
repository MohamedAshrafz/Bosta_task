package com.example.bostatask_1.ui.album

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bostatask_1.R
import com.example.bostatask_1.network.PhotoProperty

@BindingAdapter("listData")
fun bindRecyclerViewWithList(recyclerView: RecyclerView, list: List<PhotoProperty>?) {
    val adaptor = recyclerView.adapter as AlbumGridRVAdaptor
    adaptor.submitList(list)
}

@BindingAdapter("imageUrl")
// take care of the nullable String
// (after opening the app the imageUrl will be null till getting the value,
// after fetching the data from the remote server)
fun bindImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imageUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imageView)
    }
}