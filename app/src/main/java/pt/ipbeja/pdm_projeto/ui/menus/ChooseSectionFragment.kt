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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateMenu()

        /*if (args.progressDone) goToProfilesWithProgressDoneBySection()
        else goToAllProfilesBySection()*/

        binding.optionLobitos.setOnClickListener {
            findNavController().navigate(
                ChooseSectionFragmentDirections.actionChooseSectionFragmentToProfileListFragment(
                    1, args.progressDone
                )
            )
        }

        binding.optionExploradores.setOnClickListener {
            findNavController().navigate(
                ChooseSectionFragmentDirections.actionChooseSectionFragmentToProfileListFragment(
                    2, args.progressDone
                )
            )
        }

        binding.optionPioneiros.setOnClickListener {
            findNavController().navigate(
                ChooseSectionFragmentDirections.actionChooseSectionFragmentToProfileListFragment(
                    3, args.progressDone
                )
            )

        }

        binding.optionCaminheiros.setOnClickListener {
            findNavController().navigate(
                ChooseSectionFragmentDirections.actionChooseSectionFragmentToProfileListFragment(
                    4, args.progressDone
                )
            )
        }

        binding.chooseSectionBack.setOnClickListener {
            findNavController().navigate(ChooseSectionFragmentDirections.actionChooseSectionFragmentToMainMenuFragment())
        }
    }

    /*private fun goToAllProfilesBySection() {

    }

    private fun goToProfilesWithProgressDoneBySection() {
        binding.optionLobitos.setOnClickListener {
            findNavController().navigate(
                ChooseSectionFragmentDirections.actionChooseSectionFragmentToProgressDoneListFragment(
                    1
                )
            )
        }

        binding.optionExploradores.setOnClickListener {
            findNavController().navigate(
                ChooseSectionFragmentDirections.actionChooseSectionFragmentToProgressDoneListFragment(
                    2
                )
            )
        }

        binding.optionPioneiros.setOnClickListener {
            findNavController().navigate(
                ChooseSectionFragmentDirections.actionChooseSectionFragmentToProgressDoneListFragment(
                    3
                )
            )

        }

        binding.optionCaminheiros.setOnClickListener {
            findNavController().navigate(
                ChooseSectionFragmentDirections.actionChooseSectionFragmentToProgressDoneListFragment(
                    4
                )
            )
        }
    }*/

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