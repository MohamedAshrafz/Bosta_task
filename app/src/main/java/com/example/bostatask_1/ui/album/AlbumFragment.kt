package com.example.bostatask_1.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            ViewModelProvider(this, AlbumViewModelFactory(selectedAlbum))[AlbumViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = albumViewModel

        return binding.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}