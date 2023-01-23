package pt.ipbeja.pdm_projeto.db

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ProfileDao : BaseDao<Profile> {

    /**
     * This method search all the data in the table [Profile]
     *
     * @return list of all the profiles
     * */
    @Query("SELECT * FROM profile")
    fun getAll(): List<Profile>

    /**
     * This method search the profile data of the table [Profile] by the profileID
     *
     * @param profileId ID of the profile to be searched
     *
     * @return object of the class [Profile]
     * */
    @Query("SELECT * FROM profile WHERE id = :profileId")
    fun getProfile(profileId: Long): Profile

    /**
     * This method search the ID of the profile in the table [Profile]
     *
     * @param profileId ID of the profile to be searched
     *
     * @return found profile name
     * */
    @Query("SELECT name FROM profile WHERE id = :profileId")
    fun getProfileNameById(profileId: Long): String

    /**
     * This method search the ID of the profile in the table [Profile]
     *
     * @param section section to be searched
     *
     * @return all the profiles the belongs to the searched section
     * */
    @Query("SELECT * FROM profile WHERE section = :section ORDER BY name")
    fun getAllFromSection(section: String): List<Profile>

    /**
     * This method deletes the profiles that are equals to the passed ID
     *
     * @param id ID of the profile to be deleted
     *
     * @return ID of the deleted profile
     * */
    @Query("delete from profile WHERE id = :id")
    fun deleteFromID(id: Long): Int

    /**
     * This method searches for the last inserted data in the table [Profile]
     *
     * @return last ID that appears in the records sorted in ascending order
     * */
    @Query("SELECT id FROM profile ORDER BY id DESC LIMIT 1")
    fun getLastCreatedId(): Long

    /**
     * This method searches for the profile by its ID and gets the profile section
     *
     * @param profileID ID of the profile
     *
     * @return section of the profile
     * */
    @Query("SELECT section FROM profile WHERE id = :profileID")
    fun getProfileSection(profileID: Long): String

    /**
     * This method updates the name of the progress of the profile found through the ID
     *
     * @param profileID ID of the profile to be found
     * @param progressName name of the progress to be changed
     * */
    @Query("UPDATE profile SET progressName = :progressName WHERE id = :profileID")
    fun setProfileProgressName(profileID: Long, progressName: String)

    /**
     * This method searches all the profiles that belongs to a section and all the profiles
     * that has progress concluded
     *
     * @param section section to be searched
     *
     * @return list of the profiles by section with the progress concluded
     * */
    @Query("SELECT profile.* FROM profile, profileprogress WHERE profile.section = :section " +
            "and profile.id = profileprogress.profileId and profileprogress.progressDone = 1")
    fun getProfileListFromSectionAndProgressDone(section: String): List<Profile>
}