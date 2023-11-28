package com.example.bostatask_1.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bostatask_1.R
import com.example.bostatask_1.databinding.FragmentAlbumBinding
import com.example.bostatask_1.databinding.GridItemImageViewBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AlbumFragment : Fragment() {

    private var _binding: FragmentAlbumBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var albumViewModel: AlbumViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val selectedAlbum = AlbumFragmentArgs.fromBundle(requireArguments()).selectedAlbum
        albumViewModel =
            ViewModelProvider(
                this,
                AlbumViewModelFactory(selectedAlbum)
            )[AlbumViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = albumViewModel


        albumViewModel.photosList.observe(viewLifecycleOwner) { photosList ->
            for (photo in photosList) {

                val itemBinding = GridItemImageViewBinding.inflate(layoutInflater, container, false)
                bindImage(itemBinding.imageView, photo.thumbnailUrl)

                itemBinding.gridItemConstraintLayout.setOnClickListener{}

                binding.photosListGridLayout.addView(itemBinding.gridItemConstraintLayout)
            }
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}