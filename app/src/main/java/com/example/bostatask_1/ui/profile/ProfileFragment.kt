package com.example.bostatask_1.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        profileViewModel.albumsList.observe(viewLifecycleOwner) {
//            val list = listOf("hi", "there")
            for (title in it) {

//                val cv = CardView(requireContext())
//                cv.cardElevation = 5F
//                val tv = TextView(requireContext())
//                tv.text = title
//                tv.textSize = 16F
//                cv.setOnClickListener {
//                    findNavController()
//                        .navigate(ProfileFragmentDirections.actionProfileFragmentToAlbumFragment())
//                }
//                cv.addView(tv)
//                binding.linearLayoutAlbumsList.addView(cv)
//                val s = Space(requireContext())
//                s.minimumHeight = 30
//                binding.linearLayoutAlbumsList.addView(s)

                val itemBinding = AlbumListItemBinding.inflate(layoutInflater, container, false)
                itemBinding.text = title
                itemBinding.constraintLayout.setOnClickListener {
                    findNavController()
                        .navigate(ProfileFragmentDirections.actionProfileFragmentToAlbumFragment())
                }
                itemBinding.lifecycleOwner = viewLifecycleOwner
                binding.linearLayoutAlbumsList.addView(itemBinding.constraintLayout)
            }

        }
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}