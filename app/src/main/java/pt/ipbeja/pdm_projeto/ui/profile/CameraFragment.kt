package pt.ipbeja.pdm_projeto.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import pt.ipbeja.pdm_projeto.databinding.FragmentCameraBinding
import java.io.File
import java.util.*

class CameraFragment : Fragment() {

    private val viewModel: PhotoViewModel by activityViewModels()
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.camera.setLifecycleOwner(viewLifecycleOwner)

        binding.camera.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {

                val filesDir = requireContext().filesDir
                val file = File(filesDir, UUID.randomUUID().toString() + ".jpg")

                result.toFile(file) {
                    it?.run {
                        viewModel.file = it
                        viewModel.photoTaken = true
                        findNavController().popBackStack()
                    }
                }
            }
        })

        binding.shutterBtn.setOnClickListener {
            binding.camera.takePicture()
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}