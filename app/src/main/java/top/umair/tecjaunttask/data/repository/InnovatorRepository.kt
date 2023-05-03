package top.umair.tecjaunttask.data.repository


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import top.umair.tecjaunttask.data.local.InnovatorDao
import top.umair.tecjaunttask.models.InnovatorEntity
import top.umair.tecjaunttask.utils.AppConstants
import javax.inject.Inject

class InnovatorRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val innovatorDao: InnovatorDao,
    private val context: Context
) {



    suspend fun addInnovator(pojo: InnovatorEntity, ref: String, function: (Boolean) -> Unit) {
        pojo.documentId =pojo.uuid
        val documentReference = FirebaseFirestore.getInstance().collection(ref).document(pojo.documentId)

        if (isNetworkAvailable()) {
            documentReference.set(pojo, SetOptions.merge())
                .addOnSuccessListener {
                    function(true)
                    Log.d("TAG", "addData: Success")
                }
                .addOnFailureListener {
                    function(false)
                    Log.e("TAG", "addData: Error", it)

                }
            innovatorDao.insert(pojo)

        } else {
            innovatorDao.insert(pojo)
            function(true)
        }
    }


   suspend fun getAll(): Flow<List<InnovatorEntity>> {

       return flow {
            // Check network availability
            val isNetworkAvailable = isNetworkAvailable()

            // First, try to fetch data from Room
            val localData = innovatorDao.getAll()

            if (isNetworkAvailable) {
                Log.d("checking", "getAll: result")

                try {
                    // Then, fetch data from Firestore and store it in Room
                    val result = firestore.collection(AppConstants.innovators).get().await()
                    Log.d("TAG", "getAll: $result ")
                    val innovator = mutableListOf<InnovatorEntity>()
                    for (document in result.documents) {
                        document.toObject<InnovatorEntity>()?.let { innovator.add(it) }
                    }

                    // Emit the fetched data
                    emit(innovator)
//                    emit(localData)
                } catch (e: Exception) {
                    // If fetching data from Firestore fails, emit the local data
                    emit(localData)
                    throw e
                }
            } else {
                // Network is not available, emit the local data immediately
                emit(localData)
            }
        }.catch { e ->
           Log.d("checking", "getAll: $e")
        }
    }

    suspend fun backupData(acknowledge: (Boolean) -> Unit) {
        // Check network availability
        val isNetworkAvailable = isNetworkAvailable()

        if (isNetworkAvailable) {
            // Fetch all data from Room and upload to Firestore
            val localData = innovatorDao.getAll()

//            val dataMap = localData.associateBy({ it.documentId }, { it.documentId })

            localData.forEach{ innovatorEntity ->
                addInnovator(innovatorEntity,AppConstants.innovators){
                    acknowledge(it)
                }
            }


        } else {
            acknowledge(false)

//            throw IOException("Network unavailable")
        }
    }

    suspend fun restoreData(acknowledge: (Boolean) -> Unit) {
        // Check network availability
        val isNetworkAvailable = isNetworkAvailable()

        if (isNetworkAvailable) {
            // Download data from Firestore and replace all data in Room

            getAll().collect {
                // Save the fetched data in Room
                    innovatorDao.replaceAll(it)

                Log.d("meraData", ": $it")
            }

    } else {
            acknowledge(false)

//            throw IOException("Network unavailable")

        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected && (networkInfo.type == ConnectivityManager.TYPE_WIFI || networkInfo.type == ConnectivityManager.TYPE_MOBILE)
        }
    }

}
