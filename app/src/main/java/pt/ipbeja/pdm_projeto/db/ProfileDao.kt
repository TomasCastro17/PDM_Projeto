package pt.ipbeja.pdm_projeto.db

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ProfileDao : BaseDao<Profile> {

    @Query("SELECT * FROM profile")
    fun getAll(): List<Profile>

    @Query("SELECT * FROM profile WHERE id = :profileId")
    fun getProfile(profileId: Long): Profile

    @Query("SELECT name FROM profile WHERE id = :profileId")
    fun getProfileNameById(profileId: Long): String

    @Query("SELECT * FROM profile WHERE section = :section ORDER BY name")
    fun getAllFromSection(section: String): List<Profile>

    @Query("delete from profile WHERE id = :id")
    fun deleteFromID(id: Long): Int

    @Query("SELECT id FROM profile ORDER BY id DESC LIMIT 1")
    fun getLastCreatedId(): Long

    @Query("SELECT section FROM profile WHERE id = :profileID")
    fun getProfileSection(profileID: Long): String

    @Query("UPDATE profile SET progressName = :progressName WHERE id = :profileID")
    fun setProfileProgressName(profileID: Long, progressName: String)

    @Query("SELECT profile.* FROM profile, profileprogress WHERE profile.section = :section " +
            "and profile.id = profileprogress.profileId and profileprogress.progressDone = 1")
    fun getProfileListFromSectionAndProgressDone(section: String): List<Profile>
}