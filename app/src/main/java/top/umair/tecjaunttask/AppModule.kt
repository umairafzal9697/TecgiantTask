package top.umair.tecjaunttask

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import top.umair.tecjaunttask.data.local.AppDatabase
import top.umair.tecjaunttask.data.local.InnovatorDao
import top.umair.tecjaunttask.data.repository.InnovatorRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideInnovatorRepository(firestore: FirebaseFirestore, innovatorDao: InnovatorDao, @ApplicationContext context: Context) = InnovatorRepository(firestore,innovatorDao,context)


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context):AppDatabase
            = Room.databaseBuilder(context,AppDatabase::class.java,
        name = "app_database"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideInnovatorDao(appDatabase: AppDatabase): InnovatorDao {
        return appDatabase.innovatorDao()
    }

    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}