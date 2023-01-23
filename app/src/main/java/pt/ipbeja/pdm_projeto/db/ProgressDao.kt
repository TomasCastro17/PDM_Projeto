package pt.ipbeja.pdm_projeto.db

import androidx.room.Dao
import androidx.room.Query

/*
* Queries in the database for the table Progress
* Requisite 4
*
* ------------------------------------
* @authors: Tomás Jorge, Luiz Felhberg
* @numbers: 20436, 20347
*/
@Dao
interface ProgressDao : BaseDao<Progress> {

    /**
     * This method searches the progress, depending on the selected profile
     *
     * @return list of all progresses that belongs to a profile
     * */
    @Query("select * from progress, profileprogress where progress.progressId = profileprogress.progressId and profileprogress.profileId = :profileId")
    fun getAll(profileId: Long): Progress

    /**
     * This method searches for the last inserted data in the table [Progress]
     *
     * @return last ID that appears in the records sorted in ascending order
     * */
    @Query("SELECT progressId FROM progress ORDER BY progressId DESC LIMIT 1")
    fun getLastCreatedId(): Long

    /**
     * This method searches the progress ID, depending on the profile ID
     *
     * @return progress ID of the profile
     * */
    @Query("select progress.progressId from progress, profileprogress where progress.progressId = profileprogress.progressId and profileprogress.profileId = :profileId")
    fun getProgressIdFromProfileId(profileId: Long): Long
}