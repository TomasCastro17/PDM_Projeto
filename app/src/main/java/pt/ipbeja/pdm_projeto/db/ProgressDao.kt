package pt.ipbeja.pdm_projeto.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProgressDao : BaseDao<Progress> {

    //    @Query("SELECT * FROM progress WHERE ")
    @Query("select * from progress, profileprogress where progress.progressId = profileprogress.progressId and profileprogress.profileId = :profileId")
    fun getAll(profileId: Long): List<Progress>

    @Query("SELECT progressId FROM progress ORDER BY progressId DESC LIMIT 1")
    fun getLastCreatedId(): Long

    @Query("select progress.progressId from progress, profileprogress where progress.progressId = profileprogress.progressId and profileprogress.profileId = :profileId")
    fun getProgressIdFromProfileId(profileId: Long): Long
}