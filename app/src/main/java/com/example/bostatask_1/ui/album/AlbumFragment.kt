package com.example.bostatask_1.ui.album

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bostatask_1.databinding.FragmentAlbumBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.URL

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AlbumFragment : Fragment() {

    private var _binding: FragmentAlbumBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var albumViewModel: AlbumViewModel

    // Create a new coroutine scope
    private val scope = CoroutineScope(Dispatchers.Default)


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

        val adaptorRV = AlbumGridRVAdaptor {
            // Launch a new coroutine in the scope
            scope.launch {
                try {
                    val url = URL(it.url)
                    val imageData = url.readBytes()
                    val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)

                    albumViewModel.setSelectedPhoto(bitmap)
                } catch (e: Exception) {
                    albumViewModel.setShowToast()
                    Timber.tag("REPOSITORY_ERROR_STRING").e(e.stackTraceToString())
                }
            }
        }
        binding.gridList.adapter = adaptorRV

        // adding the query listener to the search bar
        binding.albumSearchBar.setOnQueryTextListener(MySearchQueryListener(albumViewModel))

        // previewing the clicked photo as full sized photo
        albumViewModel.photoSelected.observe(viewLifecycleOwner){
            binding.selectedImageImageView.setImageBitmap(it)
            binding.selectedImageImageView.visibility = View.VISIBLE
            binding.dummyLayout.visibility = View.VISIBLE
        }

        // if the user clicked anywhere on the screen other than the photo previewed exit this preview
        binding.dummyLayout.setOnClickListener {
            if (it.visibility == View.VISIBLE){
                it.visibility = View.GONE
                binding.selectedImageImageView.visibility = View.GONE
            }
        }

        // set OnClick to disable the previewed photo area from escaping
        binding.selectedImageImageView.setOnClickListener {  }

        albumViewModel.showToast.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(
                    requireContext(),
                    "Cannot connect to the server",
                    Toast.LENGTH_LONG
                ).show()
                albumViewModel.clearShowToast()
            }
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class MySearchQueryListener(
        private val viewModel: AlbumViewModel,
    ) : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query != null) {
                viewModel.setSearchText(query)
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText != null) {
                viewModel.setSearchText(newText)
            }
            return true
        }
    }
}