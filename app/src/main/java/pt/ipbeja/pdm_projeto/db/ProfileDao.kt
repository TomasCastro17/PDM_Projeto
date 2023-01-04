package pt.ipbeja.pdm_projeto.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProfileDao : BaseDao<Profile> {

    @Query("SELECT * FROM profile")
    fun getAll(): List<Profile>
}