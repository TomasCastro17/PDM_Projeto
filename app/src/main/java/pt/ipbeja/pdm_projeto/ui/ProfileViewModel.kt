package pt.ipbeja.pdm_projeto.ui

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

    fun deleteData(data: Profile) = viewModelScope.launch {
        profileDao.delete(data)
    }

    fun getProfileListFromSection(s : String): List<Profile> {
        return profileDao.getAllFromSection(s)
    }


}

