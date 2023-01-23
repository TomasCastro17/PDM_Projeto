package pt.ipbeja.pdm_projeto.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {

    /**
     * This method inserts data into the table [T]
     *
     * @param t class object
     *
     * @return ID created data into the table [T]
     * */
    @Insert
    fun insert(t: T) : Long

    /**
     * This method updates the data [t] info fromt the table [T]
     *
     * @param t class object
     *
     * @return ID of the updated data into the table [T]
     * */
    @Update
    fun update(t: T) : Int

    /**
     * This method deletes the data into from the table [T]
     *
     * @param t class object
     *
     * @return ID of the deleted data into the table [T]
     * */
    @Delete
    fun delete(t: T) : Int

}