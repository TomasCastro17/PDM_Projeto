package pt.ipbeja.pdm_projeto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.ipbeja.pdm_projeto.db.ProfileProgress
import pt.ipbeja.pdm_projeto.db.ScoutsDB

/*
* Class to send the data from Database to the a View that wants data of the table ProfileProgress
*
* ------------------------------------
* @authors: Tomás Jorge, Luiz Felhberg
* @numbers: 20436, 20347
*/
class ProfileProgressViewModel(app: Application) : AndroidViewModel(app) {

    // call the database table ProfileProgress
    private val profileProgressDao = ScoutsDB(app).profileProgressDao()

    /**
     * Method that add data to the ProfileProgress table
     *
     * @param data object of [ProfileProgress] with data
     * */
    fun addProfileProgress(data: ProfileProgress) = viewModelScope.launch {
        profileProgressDao.insert(data)
    }

    /**
     * Method to get the stage one information of the selected profile
     *
     * @param profileID ID of the selected profile
     *
     * @return true if the stage one is completed (at least one track per area)
     * */
    fun getStageOneValue(profileID: Long): Boolean = profileProgressDao.getStageOneValue(profileID)

    /**
     *  Method changes the stage of the profile and when the profile has at least one track
     *  done per area
     *
     * @param profileId ID of the selected profile
     * @param progressId ID of the progress of the selected profile
     * @param state true if the profile has made at least one tracks per area
     * */
    fun setStageOneTrue(profileId: Long, progressId: Long, state: Boolean) {
        profileProgressDao.setStageOneTrue(profileId, progressId, state)
    }

    /**
     * Method to get the stage two information of the selected profile
     *
     * @param profileID ID of the selected profile
     *
     * @return true if the stage one is completed (at least one track per area)
     * */
    fun getStageTwoValue(profileID: Long): Boolean = profileProgressDao.getStageTwoValue(profileID)

    /**
     *  Method changes the stage of the profile and when the profile has at least two tracks
     *  done per area
     *
     * @param profileId ID of the selected profile
     * @param progressId ID of the progress of the selected profile
     * @param state true if the profile has made at least two tracks per area
     * */
    fun setStageTwoTrue(profileId: Long, progressId: Long, state: Boolean) {
        profileProgressDao.setStageTwoTrue(profileId, progressId, state)
    }

    /**
     * Method to get the stage three information of the selected profile
     *
     * @param profileID ID of the selected profile
     *
     * @return true if the stage one is completed (at least one track per area)
     * */
    fun getStageThreeValue(profileID: Long): Boolean = profileProgressDao.getStageThreeValue(profileID)

    /**
     *  Method changes the stage of the profile and its used when the profile has merit or when
     * the profile doesn't deserve the merit and at least one of the tracks is not completed
     *
     * @param profileId ID of the selected profile
     * @param progressId ID of the progress of the selected profile
     * @param state true if the profile has made all the tracks and false if at least one is not
     * completed
     * */
    fun setStageThreeTrue(profileId: Long, progressId: Long, state: Boolean) {
        profileProgressDao.setStageThreeTrue(profileId, progressId, state)
    }

    /**
     * Method changes the progress of the profile and its used when the profile has merit or when
     * the profile doesn't deserve the merit and at least one of the tracks is not completed
     *
     * @param profileId ID of the selected profile
     * @param progressId ID of the progress of the selected profile
     * @param state true if the profile has made all the tracks and false if at least one is not
     * completed
     * */
    fun setProgressDone(profileId: Long, progressId: Long, state: Boolean) {
        profileProgressDao.setProgressDone(profileId, progressId, state)
    }

}