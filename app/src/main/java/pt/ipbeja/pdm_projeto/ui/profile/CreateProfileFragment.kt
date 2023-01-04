package pt.ipbeja.pdm_projeto.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipbeja.pdm_projeto.databinding.FragmentCreateProfileBinding
import pt.ipbeja.pdm_projeto.db.Profile
import pt.ipbeja.pdm_projeto.db.ScoutsDB

class CreateProfileFragment : Fragment() {

    private val viewModel: PhotoViewModel by activityViewModels()
    private lateinit var binding: FragmentCreateProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profilePicturePath = setPicture()
        binding.profileChangePhoto.setOnClickListener {
            findNavController().navigate(CreateProfileFragmentDirections.actionCreateProfileFragmentToCameraFragment())
        }

        binding.profileCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.profileCreate.setOnClickListener {
            //TODO : SAVE IN DATABASE, AND CHANGE SCREEN
            if (profilePicturePath == "") {
                Snackbar.make(view, "Precisas de tirar uma fotografia!", Snackbar.LENGTH_LONG).show()
            } else {
                val profileName = binding.profileName.text.toString()
                val profileSection = binding.profileSection.selectedItem.toString()
                val profile = Profile(profileName, profileSection, profilePicturePath)
                ScoutsDB(requireContext())
                    .profileDao()
                    .add(profile)

                Snackbar.make(view, "Perfil do $profileName criado!!", Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()

            }
        }
    }

    private fun setPicture(): String {
        viewModel.file?.let {
            binding.profilePhoto.setImageURI(it.toUri())
            return it.toUri().toString()
//            Toast.makeText(this.context, it.toUri().toString(), Toast.LENGTH_LONG).show()
        }
        return ""
    }
}