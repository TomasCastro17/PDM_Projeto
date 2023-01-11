package pt.ipbeja.pdm_projeto.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {
    @Insert
    fun insert(t: T) : Long

    @Update
    fun update(t: T) : Int

    @Delete
    fun delete(t: T) : Int

}