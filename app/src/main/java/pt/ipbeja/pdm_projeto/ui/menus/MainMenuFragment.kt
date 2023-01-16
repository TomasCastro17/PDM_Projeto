package pt.ipbeja.pdm_projeto.ui.menus

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import pt.ipbeja.pdm_projeto.databinding.FragmentMainMenuBinding

class MainMenuFragment : Fragment() {

    private lateinit var binding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainMenuBinding.inflate(inflater)
        return binding.root
    }

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
