package com.example.bostatask_1.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bostatask_1.databinding.AlbumListItemBinding
import com.example.bostatask_1.databinding.FragmentProfileBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val profileViewModel by activityViewModels<ProfileViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = profileViewModel

        // do the inflation manually (there is no need to use more sophisticated list like recycler view
        // ad this list is actually static after the initial loading)
        profileViewModel.albumsList.observe(viewLifecycleOwner) {
            for (album in it) {

                val itemBinding = AlbumListItemBinding.inflate(layoutInflater, container, false)
                itemBinding.text = album.title
                itemBinding.constraintLayout.setOnClickListener {
                    findNavController().navigate(
                        ProfileFragmentDirections.actionProfileFragmentToAlbumFragment(album)
                    )
                }
                itemBinding.lifecycleOwner = viewLifecycleOwner
                binding.albumsListLinearLayout.addView(itemBinding.constraintLayout)
            }

        }

        profileViewModel.showToast.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(
                    requireContext(),
                    "Cannot connect to the server",
                    Toast.LENGTH_LONG
                ).show()
                profileViewModel.clearShowToast()
            }
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}