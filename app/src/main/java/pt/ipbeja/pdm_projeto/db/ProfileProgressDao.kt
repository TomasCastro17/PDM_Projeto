package pt.ipbeja.pdm_projeto.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProfileProgressDao : BaseDao<ProfileProgress> {

    /**
     * This method searches the state of stage one of the profile progress
     * depending on the passed profile ID
     *
     * @param profileID ID of the selected profile
     *
     * @return profile stage value
     * */
    @Query("SELECT stageOne FROM profileprogress WHERE profileId = :profileID")
    fun getStageOneValue(profileID: Long): Boolean

    /**
     * This method updates the state of stage one of the profile progress
     *
     * @param profileId ID of the selected profile
     * @param progressId ID of the profile progress
     * @param state state of stage one to be updated
     * */
    @Query("UPDATE profileprogress SET stageOne = :state WHERE profileId = :profileId and progressId = :progressId")
    fun setStageOneTrue(profileId: Long, progressId: Long, state: Boolean)

    /**
     * This method searches the state of stage two of the profile progress
     * depending on the passed profile ID
     *
     * @param profileID ID of the selected profile
     *
     * @return profile stage value
     * */
    @Query("SELECT stageTwo FROM profileprogress WHERE profileId = :profileID")
    fun getStageTwoValue(profileID: Long): Boolean

    /**
     * This method updates the state of stage one of the profile progress
     *
     * @param profileId ID of the selected profile
     * @param progressId ID of the profile progress
     * @param state state of stage one to be updated
     * */
    @Query("UPDATE profileprogress SET stageTwo = :state WHERE profileId = :profileId and progressId = :progressId")
    fun setStageTwoTrue(profileId: Long, progressId: Long, state: Boolean)


    /**
     * This method searches the state of stage three of the profile progress
     * depending on the passed profile ID
     *
     * @param profileID ID of the selected profile
     *
     * @return profile stage value
     * */
    @Query("SELECT stageThree FROM profileprogress WHERE profileId = :profileID")
    fun getStageThreeValue(profileID: Long): Boolean

    /**
     * This method updates the state of stage one of the profile progress
     *
     * @param profileId ID of the selected profile
     * @param progressId ID of the profile progress
     * @param state state of stage one to be updated
     * */
    @Query("UPDATE profileprogress SET stageThree = :state WHERE profileId = :profileId and progressId = :progressId")
    fun setStageThreeTrue(profileId: Long, progressId: Long, state: Boolean)

    /**
     * This method updates the profile progress
     *
     * @param profileId ID of the selected profile
     * @param progressId ID of the profile progress
     * @param state state of progress to be updated
     * */
    @Query("UPDATE profileprogress SET progressDone = :state WHERE profileId = :profileId and progressId = :progressId")
    fun setProgressDone(profileId: Long, progressId: Long, state: Boolean)

}