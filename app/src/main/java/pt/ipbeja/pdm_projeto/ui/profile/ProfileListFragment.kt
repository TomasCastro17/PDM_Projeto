package pt.ipbeja.pdm_projeto.ui.profile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import pt.ipbeja.pdm_projeto.R
import pt.ipbeja.pdm_projeto.databinding.FragmentProfileListBinding
import pt.ipbeja.pdm_projeto.databinding.ProfileItemBinding
import pt.ipbeja.pdm_projeto.db.Profile
import pt.ipbeja.pdm_projeto.viewmodel.ProfileViewModel
import java.io.File

/*
* This Fragment shows the profile list, depending on the selected section
* In this Fragment we are using a RecyclerView and at lines 78 to 84 we are filling this view
* with the all the profiles
*
* ------------------------------------
* @authors: Tomás Jorge, Luiz Felhberg
* @numbers: 20436, 20347
*/
class ProfileListFragment : Fragment() {

    private lateinit var binding: FragmentProfileListBinding
    private val args: ProfileListFragmentArgs by navArgs() //arguments passed when the fragment view changes
    private val adapter = ProfileAdapter()
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileListBinding.inflate(inflater)
        return binding.root
    }

    /**
     * This method is a make sure that view is fully created and its purpose is to
     * fill the [RecyclerView] with the profiles
     *
     * @param view – The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState – If non-null, this fragment is being re-constructed from a
     * previous saved state as given here.
     * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateMenu()

        // depending on the selected section, the view will have a color, the color identifies the color of the section
        when (args.section) {
            1 -> {
                binding.section.text = getString(R.string.section_cub_scouts)
                view.setBackgroundResource(R.color.yellow)
            }
            2 -> {
                binding.section.text = getString(R.string.section_scouts)
                view.setBackgroundResource(R.color.green)
            }
            3 -> {
                binding.section.text = getString(R.string.section_venture_scout)
                view.setBackgroundResource(R.color.blue)
            }
            4 -> {
                binding.section.text = getString(R.string.section_rovers)
                view.setBackgroundResource(R.color.red)
            }
        }
        // requisite 2 - fill the RecyclerView
        // The profiles are shown, depending on which section was selected and which option the user chose in the main menu
        if (args.progressDone) { // progressDone, means that the user selected the option to see the profiles that has merit
            adapter.setData(viewModel.getProfileListFromSectionAndProgressDone(binding.section.text.toString()))
            binding.profileList.adapter = adapter
        } else {
            adapter.setData(viewModel.getProfileListFromSection(binding.section.text.toString()))
            binding.profileList.adapter = adapter
        }

        if (args.progressDone) {
            binding.profileBack.setOnClickListener {
                findNavController().navigate(
                    ProfileListFragmentDirections.actionProfileListFragmentToChooseSectionFragment(
                        true
                    )
                )
            }
        } else {
            binding.profileBack.setOnClickListener {
                findNavController().navigate(
                    ProfileListFragmentDirections.actionProfileListFragmentToChooseSectionFragment(
                        false
                    )
                )
            }
        }
    }

    /**
     * Method used to display the data for the [adapter]
     *
     * @param binding data of the fragment ProfileItem
     * */
    inner class ProfileViewHolder(private val binding: ProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var profile: Profile

        init {
            // shows a single choice dialog
            binding.root.setOnLongClickListener {
                setOnLongClickListener(it, profile, adapterPosition)
                true
            }

            // the fragment view changes and the progress of the selected profile is shown
            binding.root.setOnClickListener {
                findNavController().navigate(
                    ProfileListFragmentDirections.actionProfileListFragmentToProfileProgressFragment(
                        profile.id
                    )
                )
            }
        }

        /**
         * The method fills each item with the profile data
         *
         * @param profile - contains the profile information
         * */
        fun bind(profile: Profile) {
            this.profile = profile
            binding.profileName.text = profile.name
            binding.profileProgressName.text = profile.progressName
            val imgBitmap = BitmapFactory.decodeFile(File(profile.picturePath).absolutePath)
            binding.profilePicture.setImageBitmap(imgBitmap)
        }
    }

    /**
     * Method that contains all the [adapter] data sources
     *
     * @param profileList list of profiles that are shown
     * */
    inner class ProfileAdapter(profileList: List<Profile> = mutableListOf()) :
        RecyclerView.Adapter<ProfileViewHolder>() {

        // list of the profiles to be shown
        private val data: MutableList<Profile> = mutableListOf()

        init {
            data.addAll(profileList)
        }

        /**
         * Method called when we want to remove the selected item
         *
         * @param position list position to be removed
         * */
        fun remove(position: Int) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ProfileItemBinding.inflate(layoutInflater, parent, false)
            return ProfileViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
            val contact = data[position]
            holder.bind(contact)
        }

        /**
         * Method used to get the number of the profiles in the list
         *
         * @return the size of the profiles list
         * */
        override fun getItemCount() = data.size

        /**
         * Method that fills the [data] with all profiles list
         *
         * @param profileList list of the profiles
         * */
        fun setData(profileList: List<Profile>) {
            data.clear()
            data.addAll(profileList)
        }
    }

    /**
     * This method is called when there is an item in the [adapter] that has been long-clicked
     * and displays an alert with options to edit the selected profile or delete it
     *
     * @param view view of selected item in the [adapter]
     * @param profile selected profile in the [adapter]
     * @param adapterPosition position of the item in the [adapter] selected
     * */
    private fun setOnLongClickListener(view: View, profile: Profile, adapterPosition: Int) {
        val itemOne = getString(R.string.profile_option_1)
        val itemTwo = getString(R.string.profile_option_2)
        val listItems = arrayOf(itemOne, itemTwo)
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.alert_dialog_options_title))
            .setSingleChoiceItems(listItems, -1) { dialog, which ->
                if (listItems[which] == itemOne) {
                    findNavController().navigate(
                        ProfileListFragmentDirections.actionProfileListFragmentToCreateProfileFragment(
                            profile.id
                        )
                    )
                }
                if (listItems[which] == itemTwo) {
                    confirmDelete(view, profile, adapterPosition)
                }
                println(listItems[which])
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel_button, null)
            .show()
    }

    /**
     * This method is called when the user selected the option [R.string.profile_option_2]
     * and displays an alert to confirm the user intention on deleting the profile
     *
     * @param view view of selected item in the [adapter]
     * @param profile selected profile in the [adapter]
     * @param adapterPosition position of the item in the [adapter] selected
     * */
    private fun confirmDelete(view: View, profile: Profile, adapterPosition: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.alert_dialog_delete_option, profile.name))
            .setPositiveButton(getString(R.string.delete_button)) { _, _ ->
                run {
                    Snackbar.make(
                        view,
                        getString(R.string.snackbar_profile_deleted, profile.name),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    viewModel.deleteProfile(profile)
                    adapter.remove(adapterPosition)
                }
            }
            .setNegativeButton(getString(R.string.cancel_button), null)
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
                        findNavController().navigate(ProfileListFragmentDirections.actionProfileListFragmentToMainMenuFragment())
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}