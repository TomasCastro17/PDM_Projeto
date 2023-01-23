package pt.ipbeja.pdm_projeto.ui.menus

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import pt.ipbeja.pdm_projeto.databinding.FragmentMainMenuBinding

/*
* This fragment was created to work as a main menu to access the many tasks that a the user can do
*
* ------------------------------------
* @authors: Tomás Jorge, Luiz Felhberg
* @numbers: 20436, 20347
*/
class MainMenuFragment : Fragment() {

    private lateinit var binding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainMenuBinding.inflate(inflater)
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

        binding.optionCreateProfile.setOnClickListener {
            lifecycleScope.launch {
                findNavController().navigate(
                    MainMenuFragmentDirections.actionMainMenuFragmentToCreateProfileFragment(
                        -1
                    )
                )
            }
        }

        binding.optionViewProfiles.setOnClickListener {
            lifecycleScope.launch {
                findNavController().navigate(
                    MainMenuFragmentDirections.actionMainMenuFragmentToChooseSectionFragment(
                        false
                    )
                )
            }
        }

        binding.optionViewProgressDone.setOnClickListener {
            lifecycleScope.launch {
                findNavController().navigate(
                    MainMenuFragmentDirections.actionMainMenuFragmentToChooseSectionFragment(
                        true
                    )
                )
            }
        }

    }
}
