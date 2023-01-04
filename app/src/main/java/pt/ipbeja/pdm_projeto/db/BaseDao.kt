package pt.ipbeja.pdm_projeto.db

import androidx.room.Insert

interface BaseDao<T> {
    @Insert
    fun add(t: T) : Long
}