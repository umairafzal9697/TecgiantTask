package top.umair.tecjaunttask.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import top.umair.tecjaunttask.models.InnovatorEntity

@Database(entities = [InnovatorEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun innovatorDao(): InnovatorDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
