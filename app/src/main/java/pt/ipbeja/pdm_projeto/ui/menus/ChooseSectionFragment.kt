package pt.ipbeja.pdm_projeto.ui.menus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

}