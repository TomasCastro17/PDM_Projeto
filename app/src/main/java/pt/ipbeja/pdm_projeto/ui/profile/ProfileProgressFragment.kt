package pt.ipbeja.pdm_projeto.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import pt.ipbeja.pdm_projeto.databinding.FragmentProfileProgressBinding

class ProfileProgressFragment : Fragment() {

    private lateinit var binding: FragmentProfileProgressBinding
    private val args: ProfileProgressFragmentArgs by navArgs<ProfileProgressFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileProgressBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.profileProgression.text = "profileID = ${args.profileID}"
        binding.profileProgression.text = binding.progressCheckboxAfetivoAutoestima.isChecked.toString()
        binding.progressCheckboxAfetivoAutoestima.setOnClickListener {
            binding.profileProgression.text = binding.progressCheckboxAfetivoAutoestima.isChecked.toString()
        }
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnConfirm.setOnClickListener {
            //TODO guardar na BD
            findNavController().popBackStack()
        }
    }

    fun fillCheckboxes() {
        lifecycleScope.launch {
            binding.progressCheckboxAfetivoAutoestima.isChecked
        }
    }
}