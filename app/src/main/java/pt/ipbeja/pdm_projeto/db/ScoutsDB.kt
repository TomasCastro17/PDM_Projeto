package pt.ipbeja.pdm_projeto.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Profile::class, Progress::class, ProfileProgress::class],
    version = 7,
    exportSchema = false
)
abstract class ScoutsDB : RoomDatabase() {

    abstract fun profileDao(): ProfileDao
    abstract fun profileProgressDao(): ProfileProgressDao
    abstract fun progressDao(): ProgressDao

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