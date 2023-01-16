package pt.ipbeja.pdm_projeto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import pt.ipbeja.pdm_projeto.db.Progress
import pt.ipbeja.pdm_projeto.db.ScoutsDB

class ProgressViewModel (app: Application) : AndroidViewModel(app) {

    private val progressDao = ScoutsDB(app).progressDao()

    fun addProgress(data: Progress) = viewModelScope.launch {
        progressDao.insert(data)
    }

    fun updateProgress(data: Progress) = viewModelScope.launch {
        progressDao.update(data)
    }

    fun getAllFromProfileId(profileId: Long): List<Progress> = progressDao.getAll(profileId)

    fun getLastCreatedId(): Long = progressDao.getLastCreatedId()

    fun getProgressIdFromProfileId(profileId: Long) : Long = progressDao.getProgressIdFromProfileId(profileId)
}