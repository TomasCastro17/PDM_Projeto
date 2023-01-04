package pt.ipbeja.pdm_projeto.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Profile::class], version = 3, exportSchema = false)
abstract class ScoutsDB : RoomDatabase() {

    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var instance: ScoutsDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context.applicationContext).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, ScoutsDB::class.java, "scouts.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries() // for now :)
                .build()
    }
}