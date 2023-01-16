package pt.ipbeja.pdm_projeto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.ipbeja.pdm_projeto.db.Profile
import pt.ipbeja.pdm_projeto.db.ScoutsDB

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val profileDao = ScoutsDB(app).profileDao()

    fun addProfile(data: Profile) = viewModelScope.launch {
        profileDao.insert(data)
    }

    fun deleteProfile(data: Profile) = viewModelScope.launch {
        profileDao.delete(data)
    }

    fun getProfile(profileID: Long) = profileDao.getProfile(profileID)

    fun getProfileNameById(id: Long): String = profileDao.getProfileNameById(id)

    //    fun getProfileListFromSection(s : String): Flow<List<Profile>> = profileDao.getAllFromSection(s)
    fun getProfileListFromSection(s: String): List<Profile> = profileDao.getAllFromSection(s)

    fun getLastCreatedId(): Long = profileDao.getLastCreatedId()

    fun getProfileSection(profileID: Long): String = profileDao.getProfileSection(profileID)

    fun setProfileProgressName(profileID: Long, progressName: String) {
        profileDao.setProfileProgressName(profileID, progressName)
    }

    fun updateProfile(profile: Profile) {
        profileDao.update(profile)
    }

    fun getProfileListFromSectionAndProgressDone(section: String): List<Profile> = profileDao.getProfileListFromSectionAndProgressDone(section)
}

