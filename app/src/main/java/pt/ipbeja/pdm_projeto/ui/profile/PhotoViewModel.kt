package pt.ipbeja.pdm_projeto.ui.profile

import androidx.lifecycle.ViewModel
import java.io.File

class PhotoViewModel: ViewModel() {
    var file: File? = null
    var photoTaken = false
}