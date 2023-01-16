package pt.ipbeja.pdm_projeto.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProfileProgressDao: BaseDao<ProfileProgress> {

    @Query("SELECT stageOne FROM profileprogress WHERE profileId = :profileID")
    fun getStageOneValue(profileID: Long): Boolean

    @Query("UPDATE profileprogress SET stageOne = :state WHERE profileId = :profileId and progressId = :progressId")
    fun setStageOneTrue(profileId: Long, progressId: Long, state: Boolean)

    @Query("SELECT stageTwo FROM profileprogress WHERE profileId = :profileID")
    fun getStageTwoValue(profileID: Long): Boolean

    @Query("UPDATE profileprogress SET stageTwo = :state WHERE profileId = :profileId and progressId = :progressId")
    fun setStageTwoTrue(profileId: Long, progressId: Long, state: Boolean)

    @Query("SELECT stageThree FROM profileprogress WHERE profileId = :profileID")
    fun getStageThreeValue(profileID: Long): Boolean

    @Query("UPDATE profileprogress SET stageThree = :state WHERE profileId = :profileId and progressId = :progressId")
    fun setStageThreeTrue(profileId: Long, progressId: Long, state: Boolean)

    @Query("UPDATE profileprogress SET progressDone = :state WHERE profileId = :profileId and progressId = :progressId")
    fun setProgressDone(profileId: Long, progressId: Long, state: Boolean)

}