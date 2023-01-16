package pt.ipbeja.pdm_projeto.ui.profile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.net.toUri
import androidx.core.view.MenuProvider
import androidx.core.view.marginTop
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import pt.ipbeja.pdm_projeto.R
import pt.ipbeja.pdm_projeto.databinding.FragmentCreateProfileBinding
import pt.ipbeja.pdm_projeto.db.Profile
import pt.ipbeja.pdm_projeto.db.ProfileProgress
import pt.ipbeja.pdm_projeto.db.Progress
import pt.ipbeja.pdm_projeto.viewmodel.ProfileProgressViewModel
import pt.ipbeja.pdm_projeto.viewmodel.ProfileViewModel
import pt.ipbeja.pdm_projeto.viewmodel.ProgressViewModel
import java.io.File

class CreateProfileFragment : Fragment() {

    private val photoViewModel: PhotoViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val progressViewModel: ProgressViewModel by activityViewModels()
    private val profileProgressViewModel: ProfileProgressViewModel by activityViewModels()

    private val args: CreateProfileFragmentArgs by navArgs()

    private lateinit var binding: FragmentCreateProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateMenu()

        val profilePicturePath = setPicture()
        binding.profileChangePhoto.setOnClickListener {
            lifecycleScope.launch {
                findNavController().navigate(CreateProfileFragmentDirections.actionCreateProfileFragmentToCameraFragment())
            }
        }

        if (args.profileID == -1L) createProfile(profilePicturePath)
        else editProfile(profilePicturePath)
    }

    private fun createProfile(profilePicturePath: String) {

        binding.profileCancel.setOnClickListener {
            findNavController().popBackStack()
            photoViewModel.photoTaken = false
        }

        binding.profileCreate.setOnClickListener {
            if (profilePicturePath == "") {
                Snackbar.make(
                    requireView(),
                    "Precisas de tirar uma fotografia!",
                    Snackbar.LENGTH_LONG
                )
                    .show()

            } else if (binding.profileName.text.toString() != "") {
                val profileName = binding.profileName.text.toString()
                val profileSection = binding.profileSection.selectedItem.toString()
                addDataToTables(profileName, profileSection, profilePicturePath)
                findNavController().navigate(
                    CreateProfileFragmentDirections.actionCreateProfileFragmentToProfileListFragment(
                        binding.profileSection.selectedItemPosition + 1, progressDone = false
                    )
                )
                photoViewModel.photoTaken = false
            }
        }
    }

    private fun editProfile(profilePicturePath: String) {
        val profile: Profile = profileViewModel.getProfile(args.profileID)
        binding.profileName.text = profile.name.toEditable()
        when (profile.section) {
            "Lobitos" -> binding.profileSection.setSelection(0)
            "Exploradores" -> binding.profileSection.setSelection(1)
            "Pioneiros" -> binding.profileSection.setSelection(2)
            "Caminheiros" -> binding.profileSection.setSelection(3)
        }
        val imgBitmap = BitmapFactory.decodeFile(File(profile.picturePath).absolutePath)
        binding.profilePhoto.setImageBitmap(imgBitmap)
        binding.profilePhoto.rotation = 90.0F
        binding.profilePhoto.layoutParams.width = 410

        binding.profileCancel.setOnClickListener {
            findNavController().popBackStack()
            photoViewModel.photoTaken = false
        }

        binding.profileCreate.setOnClickListener {
            if (binding.profileName.text.toString() != "") {
                val profileName = binding.profileName.text.toString()
                val profileSection = binding.profileSection.selectedItem.toString()
                updateProfile(profileName, profileSection, profilePicturePath, profile.progressName)
                findNavController().navigate(
                    CreateProfileFragmentDirections.actionCreateProfileFragmentToProfileListFragment(
                        binding.profileSection.selectedItemPosition + 1, false
                    )
                )
                photoViewModel.photoTaken = false
            }
        }
    }


    private fun setPicture(): String {
        photoViewModel.file?.let {
            if (photoViewModel.photoTaken) {
                val filepath = it.toUri().toString().split(":")[1]
                val imgBitmap = BitmapFactory.decodeFile(File(filepath).absolutePath)
                binding.profilePhoto.setImageBitmap(imgBitmap)
                binding.profilePhoto.rotation = 90.0F
                binding.profilePhoto.layoutParams.width = 410
                return filepath
            }
        }
        return ""
    }

    private fun addDataToTables(
        profileName: String, profileSection: String, profilePicturePath: String
    ) {
        var progressName = ""
        when (profileSection) {
            "Lobitos" -> progressName = "Pata Tenra"
            "Exploradores" -> progressName = "Apelo"
            "Pioneiros" -> progressName = "Desprendimento"
            "Caminheiros" -> progressName = "Caminho"
        }

        profileViewModel.addProfile(
            Profile(
                profileName,
                profileSection,
                profilePicturePath,
                progressName
            )
        )
        progressViewModel.addProgress(Progress())

        val profileId = profileViewModel.getLastCreatedId()
        val progressId = progressViewModel.getLastCreatedId()
        profileProgressViewModel.addProfileProgress(
            ProfileProgress(profileId = profileId, progressId = progressId)
        )

        Snackbar.make(requireView(), "Perfil do $profileName criado!!", Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun updateProfile(
        profileName: String,
        profileSection: String,
        profilePicturePath: String,
        progressName: String
    ) {
        profileViewModel.updateProfile(
            Profile(
                profileName,
                profileSection,
                profilePicturePath,
                progressName
            )
        )
        Snackbar.make(requireView(), "Perfil do $profileName alterado!!", Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun generateMenu() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.home -> {
                        findNavController().navigate(CreateProfileFragmentDirections.actionCreateProfileFragmentToMainMenuFragment())
                        photoViewModel.photoTaken = false
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}
