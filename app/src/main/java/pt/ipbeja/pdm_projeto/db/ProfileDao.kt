package pt.ipbeja.pdm_projeto.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProfileDao : BaseDao<Profile> {

    @Query("SELECT * FROM profile")
    fun getAll(): List<Profile>

    @Query("SELECT * FROM profile WHERE section = :section")
    fun getAllFromSection(section: String): List<Profile>

    @Query("delete from profile WHERE id = :id")
    fun deleteFromID(id: Long) : Int
}