package pt.ipbeja.pdm_projeto.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
* Class that initializes and creates the database
* Requisite 4
*
* ------------------------------------
* @authors: Tomás Jorge, Luiz Felhberg
* @numbers: 20436, 20347
*/
@Database(
    entities = [Profile::class, Progress::class, ProfileProgress::class],
    version = 8,
    exportSchema = false
)
abstract class ScoutsDB : RoomDatabase() {

    abstract fun profileDao(): ProfileDao
    abstract fun progressDao(): ProgressDao
    abstract fun profileProgressDao(): ProfileProgressDao

    /**
     * Initialization of the database
     * */
    companion object {
        @Volatile
        private var instance: ScoutsDB? = null
        private val lock: Any = Any()

        operator fun invoke(context: Context): ScoutsDB {
            if (instance != null) return instance!!
            synchronized(lock) {
                if (instance != null) return instance!!
                instance = Room.databaseBuilder(context, ScoutsDB::class.java, "scouts.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                return instance!!
            }

        }
    }
}