package top.umair.tecjaunttask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import top.umair.tecjaunttask.models.InnovatorEntity
@Dao
interface InnovatorDao {

    @Query("SELECT * FROM innovator")
    fun getAll(): List<InnovatorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(innovators: List<InnovatorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(innovator: InnovatorEntity)


    @Query("DELETE FROM innovator")
    suspend fun deleteAll()

    @Transaction
    suspend fun replaceAll(innovators: List<InnovatorEntity>) {
        deleteAll()
        insertAll(innovators)
    }

    @Update
    suspend fun update(innovator: InnovatorEntity)
}
    // Modified function to upload data to Firebase
