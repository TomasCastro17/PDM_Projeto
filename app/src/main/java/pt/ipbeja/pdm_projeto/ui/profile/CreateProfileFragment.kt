package pt.ipbeja.pdm_projeto.ui.profile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.net.toUri
import androidx.core.view.MenuProvider
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
import pt.ipbeja.pdm_projeto.viewmodel.PhotoViewModel
import pt.ipbeja.pdm_projeto.viewmodel.ProfileProgressViewModel
import pt.ipbeja.pdm_projeto.viewmodel.ProfileViewModel
import pt.ipbeja.pdm_projeto.viewmodel.ProgressViewModel
import java.io.File

/*
* This fragment is a form for the user to create the Scouts profile
*
* ------------------------------------
* @authors: Tomás Jorge, Luiz Felhberg
* @numbers: 20436, 20347
*/
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

    /**
     * This method is a make sure that view is fully created and its purpose is to create a new
     * profile, with the data inserted by the user
     *
     * @param view – The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState – If non-null, this fragment is being re-constructed from a
     * previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateMenu()

        // Save the picture file path
        val profilePicturePath = setPicture()

        // When the button "profileChangePhoto" is clicked, the view is changed, so the user can take a picture to identity the profile
        binding.profileChangePhoto.setOnClickListener {
            lifecycleScope.launch {
                findNavController().navigate(CreateProfileFragmentDirections.actionCreateProfileFragmentToCameraFragment())
            }
        }

        // Confirmation the user selected the main menu option to create a new profile
        if (args.profileID == -1L) createProfile(profilePicturePath)
        // Confirmation the user selected the profile option to edit a profile
        else editProfile(profilePicturePath)

        // Listener to cancel the data, so nothing is created or edited
        binding.profileCancel.setOnClickListener {
            findNavController().popBackStack()
            // confirmation for the next time that the user open this view, there isn't a photo
            photoViewModel.photoTaken = false
        }
    }

    /**
     * This method creates a new profile and save the data in Database
     *
     * @param profilePicturePath - contains the path of photo taken for the profile
     * */
    private fun createProfile(profilePicturePath: String) {

        // Listener to create the new profile
        binding.profileCreate.setOnClickListener {
            // confirm that the data inputs are completed, it's completed, the view is changed
            if (profilePicturePath == "") {
                Snackbar.make(
                    requireView(), "Precisas de tirar uma fotografia!", Snackbar.LENGTH_LONG
                ).show()

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

    /**
     * This method gets the information of the selected profile, to fill into the
     * existing inputs
     *
     * @param profilePicturePath - contains the path of photo taken for the profile
     * */
    private fun editProfile(profilePicturePath: String) {
        // gets the info of the selected profile
        val profile: Profile = profileViewModel.getProfile(args.profileID)
        // fills the name input with the selected profile name and transform it into a String
        binding.profileName.text = profile.name.toEditable()
        // put the section of the profile as the selected
        when (profile.section) {
            "Lobitos" -> binding.profileSection.setSelection(0)
            "Exploradores" -> binding.profileSection.setSelection(1)
            "Pioneiros" -> binding.profileSection.setSelection(2)
            "Caminheiros" -> binding.profileSection.setSelection(3)
        }
        // Transform the picture path into a bitmap
        val imgBitmap = BitmapFactory.decodeFile(File(profile.picturePath).absolutePath)
        binding.profilePhoto.setImageBitmap(imgBitmap)
        binding.profilePhoto.rotation = 90.0F
        binding.profilePhoto.layoutParams.width = 410

        // Listener to change the profile with the new changed data
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

    /**
     * This method verify if the [photoViewModel] File existing and if was taken on this
     * view creation
     *
     * @return null if the photo wasn't taken yet and if the photo was taken a String to
     * the photo path
     * */
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

    /**
     * This method creates the necessary data for the profile, such as his profile
     * and his progress, and saves it to the database
     *
     * @param profileName - this String contains the profile name to be created
     * @param profileSection - this String contains the section that the profile belongs to
     * @param profilePicturePath - this String contains the path of the profile photo
     * */
    private fun addDataToTables(
        profileName: String, profileSection: String, profilePicturePath: String
    ) {
        // fills in the progressName of the profile, since this depends on which section the profile belongs to
        var progressName = ""
        when (profileSection) {
            "Lobitos" -> progressName = "Pata Tenra"
            "Exploradores" -> progressName = "Apelo"
            "Pioneiros" -> progressName = "Desprendimento"
            "Caminheiros" -> progressName = "Caminho"
        }

        // creates a new profile in database
        profileViewModel.addProfile(
            Profile(
                profileName, profileSection, profilePicturePath, progressName
            )
        )
        // created a new progress to associate it with the profile
        progressViewModel.addProgress(Progress())

        // saves the ID of the newly created profile
        val profileId = profileViewModel.getLastCreatedId()
        // saves the ID of the newly created progress
        val progressId = progressViewModel.getLastCreatedId()
        // associate the profile to the progress
        profileProgressViewModel.addProfileProgress(
            ProfileProgress(profileId = profileId, progressId = progressId)
        )

        Snackbar.make(requireView(), "Perfil do $profileName criado!!", Snackbar.LENGTH_SHORT)
            .show()
    }

    /**
     * This method changes the profile data, and saves it to the database
     *
     * @param profileName - this String contains the profile name to be created
     * @param profileSection - this String contains the section that the profile belongs to
     * @param profilePicturePath - this String contains the path of the profile photo
     * */
    private fun updateProfile(
        profileName: String,
        profileSection: String,
        profilePicturePath: String,
        progressName: String
    ) {
        profileViewModel.updateProfile(
            Profile(
                profileName, profileSection, profilePicturePath, progressName
            )
        )
        Snackbar.make(requireView(), "Perfil do $profileName alterado!!", Snackbar.LENGTH_SHORT)
            .show()
    }

    /**
     * Method that creates an option in the app menu to go to the main menu
     */
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

    /**
     * This method transforms a String into a Editable property
     *
     * https://stackoverflow.com/a/51799663
     * */
    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}
