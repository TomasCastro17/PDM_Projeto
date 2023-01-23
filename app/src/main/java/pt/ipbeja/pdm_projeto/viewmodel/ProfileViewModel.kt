package pt.ipbeja.pdm_projeto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.ipbeja.pdm_projeto.db.Profile
import pt.ipbeja.pdm_projeto.db.ScoutsDB

/*
* Class to send the data from Database to the a View that wants data of the table Profile
*
* ------------------------------------
* @authors: Tomás Jorge, Luiz Felhberg
* @numbers: 20436, 20347
*/
class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    // call the database table Profile
    private val profileDao = ScoutsDB(app).profileDao()

    /**
     * This method calls the
     *
     * @param data object [Profile] that contains the info of the profile to be created
     * */
    fun addProfile(data: Profile) = viewModelScope.launch {
        profileDao.insert(data)
    }

    /**
     * This method sends the profile data to the method that interacts with the database to
     * delete the selected profile
     *
     * @param data object [Profile] to be deleted
     * */
    fun deleteProfile(data: Profile) = viewModelScope.launch {
        profileDao.delete(data)
    }

    /**
     * This method sends the profile id to the method that interacts with the database
     *
     * @param profileID ID of the selected profile
     *
     * @return profile of the selected profile depending on its ID
     * */
    fun getProfile(profileID: Long) = profileDao.getProfile(profileID)

    /**
     * This method sends the profile id to the method that interacts with the database
     *
     * @return profile name
     * */
    fun getProfileNameById(id: Long): String = profileDao.getProfileNameById(id)


    /**
     * This method sends the selected section, to the method that interacts with the database,
     * in order to search for all the profiles that belongs to the selected section
     *
     * @param s selected section
     *
     * @return list of [Profile] found in database that belongs to the selected section
     * */
    fun getProfileListFromSection(s: String): List<Profile> = profileDao.getAllFromSection(s)

    /**
     * The purpose of this method is to associate the profile with the progress,
     * so a record is made and after that the method is called and will return
     * the ID of that record made.
     *
     * @return last ID that appears in the records sorted in ascending order
     * */
    fun getLastCreatedId(): Long = profileDao.getLastCreatedId()

    /**
     * This method sends the profile ID, to the method that interacts with the database,
     * in order to look up the profile information and receive the section the profile belongs to
     *
     * @param profileID ID of the selected profile
     *
     * @return section that the profile belongs to
     * */
    fun getProfileSection(profileID: Long): String = profileDao.getProfileSection(profileID)

    /**
     * This method sends the profile ID to be searched and the progress name to be updated
     * depending on the profile ID
     *
     * @param profileID ID of the selected profile
     * @param progressName progress name to be updated to the profile
     * */
    fun setProfileProgressName(profileID: Long, progressName: String) {
        profileDao.setProfileProgressName(profileID, progressName)
    }

    /**
     * This method sends the new profile information to update his information in the database
     *
     * @return list of [Profile] depending on section
     * */
    fun updateProfile(profile: Profile) {
        profileDao.update(profile)
    }

    /**
     * This method sends the chosen section to the method which interacts with the
     * database and receives the list of profiles belonging to the chosen section
     *
     * @return list of [Profile] depending on section
     * */
    fun getProfileListFromSectionAndProgressDone(section: String): List<Profile> =
        profileDao.getProfileListFromSectionAndProgressDone(section)
}

