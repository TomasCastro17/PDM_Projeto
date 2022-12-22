package pt.ipbeja.pdm_projeto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import pt.ipbeja.pdm_projeto.databinding.FragmentCreateProfileBinding

class CreateProfileFragment : Fragment() {

    private val viewModel: PhotoViewModel by activityViewModels()
    private lateinit var binding: FragmentCreateProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPicture()

        binding.profileChangePhoto.setOnClickListener {
            findNavController().navigate(CreateProfileFragmentDirections.actionCreateProfileFragmentToCameraFragment())
        }

        binding.profileCreate.setOnClickListener {
            //TODO : SAVE IN DATABASE, AND CHANGE SCREEN
        }
    }

    private fun setPicture() {
        viewModel.file?.let {
            binding.profilePhoto.setImageURI(it.toUri())
//            binding.profilePhoto.layoutParams.height = 300
        }
    }
}