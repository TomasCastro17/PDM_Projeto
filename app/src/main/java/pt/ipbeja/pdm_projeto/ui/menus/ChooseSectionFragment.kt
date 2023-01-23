package pt.ipbeja.pdm_projeto.ui.menus

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import pt.ipbeja.pdm_projeto.R
import pt.ipbeja.pdm_projeto.databinding.FragmentChooseSectionBinding
import pt.ipbeja.pdm_projeto.ui.profile.CreateProfileFragmentDirections
import pt.ipbeja.pdm_projeto.ui.profile.ProfileListFragmentArgs
import pt.ipbeja.pdm_projeto.viewmodel.ProfileViewModel

/*
* Since there are four different sections in the Scouts, the user has to choose
* the section to access the profiles that belong to the selected section
*
* ------------------------------------
* @authors: Tomás Jorge, Luiz Felhberg
* @numbers: 20436, 20347
*/
class ChooseSectionFragment : Fragment() {

    private lateinit var binding: FragmentChooseSectionBinding
    private val args: ChooseSectionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseSectionBinding.inflate(inflater)
        return binding.root
    }

    /**
     * This method is a make sure that view is fully created and since this is an options menu
     * its purpose is just to have listeners for the buttons so that when they are clicked
     * the application changes the view fragment
     *
     * @param view – The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState – If non-null, this fragment is being re-constructed from a
     * previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Generate the app menu on top right
        generateMenu()

        // Click listener on the button "optionLobitos", to change to the ProfileListFragment
        binding.optionLobitos.setOnClickListener {
            findNavController().navigate(
                ChooseSectionFragmentDirections.actionChooseSectionFragmentToProfileListFragment(
                    1, args.progressDone
                )
            )
        }

        // Click listener on the button "optionExploradores", to change to the ProfileListFragment
        binding.optionExploradores.setOnClickListener {
            findNavController().navigate(
                ChooseSectionFragmentDirections.actionChooseSectionFragmentToProfileListFragment(
                    2, args.progressDone
                )
            )
        }

        // Click listener on the button "optionPioneiros", to change to the ProfileListFragment
        binding.optionPioneiros.setOnClickListener {
            findNavController().navigate(
                ChooseSectionFragmentDirections.actionChooseSectionFragmentToProfileListFragment(
                    3, args.progressDone
                )
            )

        }

        // Click listener on the button "optionCaminheiros", to change to the ProfileListFragment
        binding.optionCaminheiros.setOnClickListener {
            findNavController().navigate(
                ChooseSectionFragmentDirections.actionChooseSectionFragmentToProfileListFragment(
                    4, args.progressDone
                )
            )
        }

        // Click listener on the button "chooseSectionBack", to change to the MainMenuFragment
        binding.chooseSectionBack.setOnClickListener {
            findNavController().navigate(ChooseSectionFragmentDirections.actionChooseSectionFragmentToMainMenuFragment())
        }
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
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}