package top.umair.tecjaunttask

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import top.umair.tecjaunttask.data.local.AppDatabase
import top.umair.tecjaunttask.data.local.InnovatorDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideStudentDaoDao(appDatabase: AppDatabase): InnovatorDao {
        return appDatabase.schoolDao()
    }
}