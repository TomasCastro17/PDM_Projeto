package pt.ipbeja.pdm_projeto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.ipbeja.pdm_projeto.db.ProfileProgress
import pt.ipbeja.pdm_projeto.db.ScoutsDB

class ProfileProgressViewModel(app: Application) : AndroidViewModel(app) {

    private val profileProgressDao = ScoutsDB(app).profileProgressDao()

    fun addProfileProgress(data: ProfileProgress) = viewModelScope.launch {
        profileProgressDao.insert(data)
    }

    fun getStageOneValue(profileID: Long): Boolean = profileProgressDao.getStageOneValue(profileID)

    fun setStageOneTrue(profileId: Long, progressId: Long, state: Boolean) {
        profileProgressDao.setStageOneTrue(profileId, progressId, state)
    }

    fun getStageTwoValue(profileID: Long): Boolean = profileProgressDao.getStageTwoValue(profileID)

    fun setStageTwoTrue(profileId: Long, progressId: Long, state: Boolean) {
        profileProgressDao.setStageTwoTrue(profileId, progressId, state)
    }

    fun getStageThreeValue(profileID: Long): Boolean = profileProgressDao.getStageThreeValue(profileID)

    fun setStageThreeTrue(profileId: Long, progressId: Long, state: Boolean) {
        profileProgressDao.setStageThreeTrue(profileId, progressId, state)
    }

    fun setProgressDone(profileId: Long, progressId: Long, state: Boolean) {
        profileProgressDao.setProgressDone(profileId, progressId, state)
    }

}