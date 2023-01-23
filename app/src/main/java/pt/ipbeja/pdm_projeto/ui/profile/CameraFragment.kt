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
import pt.ipbeja.pdm_projeto.viewmodel.PhotoViewModel
import java.io.File
import java.util.*

/*
* This fragment shows the phone's camera view
* In this Fragment the user has to take a picture and the picture will be saved in the
* phone's file
*
* This code was based on the CameraViewDemo app:
* https://ipbejapt.sharepoint.com/:u:/r/sites/PDM-2022-2023/Documentos%20Partilhados/Recursos/CameraViewDemo.zip?csf=1&web=1&e=evEiwg
*
* ------------------------------------
* @authors: Tomás Jorge, Luiz Felhberg
* @numbers: 20436, 20347
*/
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

    /**
     * This method is a make sure that view is fully created and its purpose is to save the
     * photo file when the shutter button is clicked
     *
     * @param view – The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState – If non-null, this fragment is being re-constructed from a
     * previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This method set a lifecycle for the view, so the user doesn't have to open or close or destroy it
        binding.camera.setLifecycleOwner(viewLifecycleOwner)

        // The view has a listener to know when the photo is taken
        binding.camera.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {

                val filesDir = requireContext().filesDir
                val file = File(filesDir, UUID.randomUUID().toString() + ".jpg")

                // Requisite 3 e 4 - save the photo as file
                result.toFile(file) {
                    it?.run {
                        viewModel.file = it
                        viewModel.photoTaken = true
                        findNavController().popBackStack()
                    }
                }
            }
        })

        // Listener to the shutter button, to take the picture
        binding.shutterBtn.setOnClickListener {
            binding.camera.takePicture()
        }

        // Listener to change the view without taking the picture
        binding.cancelBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    /**
     * This method set the [_binding] null when the view is destroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}