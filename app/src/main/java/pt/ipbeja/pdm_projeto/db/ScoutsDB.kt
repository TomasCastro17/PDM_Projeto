package pt.ipbeja.pdm_projeto.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Profile::class], version = 3, exportSchema = false)
abstract class ScoutsDB : RoomDatabase() {

    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var instance: ScoutsDB? = null
        private val lock: Any = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, ScoutsDB::class.java, "scouts.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()



    /*operator fun invoke(context: Context): ScoutsDB {
               if (instance != null) return instance!!
               synchronized(lock) {
                   if (instance != null) return instance!!
                   instance = Room.databaseBuilder(context, ScoutsDB::class.java, "scoutsdb")
                       .fallbackToDestructiveMigration()
                       .build()

                   return instance!!
               }

           }*/
    }
}