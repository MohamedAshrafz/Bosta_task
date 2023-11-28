package com.example.bostatask_1.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bostatask_1.databinding.FragmentAlbumBinding

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

        val adaptorRV = AlbumGridRVAdaptor({
            // TODO
        })
        binding.gridList.adapter = adaptorRV

        binding.albumSearchBar.setOnQueryTextListener(MySearchQueryListener(albumViewModel))

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