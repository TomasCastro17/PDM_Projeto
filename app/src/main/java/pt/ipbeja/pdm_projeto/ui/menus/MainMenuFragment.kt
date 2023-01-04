package pt.ipbeja.pdm_projeto.ui.menus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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
            findNavController().navigate(MainMenuFragmentDirections.actionMainMenuFragmentToCreateProfileFragment())
        }

//        binding.imageView.setImageURI("file:///data/user/0/pt.ipbeja.pdm_projeto/files/pic.jpg".toUri())
    }

}