package pt.ipbeja.pdm_projeto.viewmodel

import androidx.lifecycle.ViewModel
import java.io.File

/*
* Class to save the photo file and register if the photo was taken or not
*
* ------------------------------------
* @authors: Tomás Jorge, Luiz Felhberg
* @numbers: 20436, 20347
*/
class PhotoViewModel: ViewModel() {
    // when the picture is taken, the file is saved here
    var file: File? = null
    // when the picture is taken, this value becomes true
    var photoTaken = false
}