package pt.ipbeja.pdm_projeto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import pt.ipbeja.pdm_projeto.db.ProfileProgress
import pt.ipbeja.pdm_projeto.db.Progress
import pt.ipbeja.pdm_projeto.db.ScoutsDB

/*
* Class to send the data from Database to the a View that wants data of the table Progress
*
* ------------------------------------
* @authors: Tomás Jorge, Luiz Felhberg
* @numbers: 20436, 20347
*/
class ProgressViewModel (app: Application) : AndroidViewModel(app) {

    // call the database table Progress
    private val progressDao = ScoutsDB(app).progressDao()

    /**
     * Method that add data to the Progress table
     *
     * @param data object of [Progress] with data
     * */
    fun addProgress(data: Progress) = viewModelScope.launch {
        progressDao.insert(data)
    }

    /**
     * Method that updates the data in the Progress table, for a given id, that is referenced to the profile
     *
     * @param data object of [Progress] with data
     * */
    fun updateProgress(data: Progress) = viewModelScope.launch {
        progressDao.update(data)
    }

    /**
     * The purpose of this method is to get the progress of the selected profile by its ID
     *
     * @param profileId ID of the selected profile
     *
     * @return [Progress] depending of the profileID
     * */
    fun getAllFromProfileId(profileId: Long): Progress = progressDao.getAll(profileId)

    /**
     * The purpose of this method is to associate the profile with the progress,
     * so a record is made and after that the method is called and will return
     * the ID of that record made.
     *
     * @return last ID that appears in the records sorted in ascending order
     * */
    fun getLastCreatedId(): Long = progressDao.getLastCreatedId()

    /**
     * This method returns the progress ID that is associated with the selected profile
     *
     * @param profileId ID of the selected profile
     *
     * @return progress ID of the selected profile
     * */
    fun getProgressIdFromProfileId(profileId: Long) : Long = progressDao.getProgressIdFromProfileId(profileId)
}