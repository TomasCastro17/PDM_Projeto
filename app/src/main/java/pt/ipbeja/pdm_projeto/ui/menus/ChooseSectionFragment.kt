package pt.ipbeja.pdm_projeto.ui.menus

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import pt.ipbeja.pdm_projeto.databinding.FragmentChooseSectionBinding
import pt.ipbeja.pdm_projeto.databinding.FragmentCreateProfileBinding

class ChooseSectionFragment : Fragment() {

    private lateinit var binding: FragmentChooseSectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseSectionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.optionLobitos.setOnClickListener {
            findNavController().navigate(ChooseSectionFragmentDirections.actionChooseSectionFragmentToProfileListFragment(1))
        }

        binding.optionExploradores.setOnClickListener {
            findNavController().navigate(ChooseSectionFragmentDirections.actionChooseSectionFragmentToProfileListFragment(2))

        }

        binding.optionPioneiros.setOnClickListener {
            findNavController().navigate(ChooseSectionFragmentDirections.actionChooseSectionFragmentToProfileListFragment(3))

        }

        binding.optionCaminheiros.setOnClickListener {
            findNavController().navigate(ChooseSectionFragmentDirections.actionChooseSectionFragmentToProfileListFragment(4))

        }
    }

}