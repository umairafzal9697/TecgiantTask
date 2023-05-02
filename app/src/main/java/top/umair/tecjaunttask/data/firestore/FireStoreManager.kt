package top.umair.tecjaunttask.data.firestore

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import top.umair.tecjaunttask.models.InnovatortEntity
import top.umair.tecjaunttask.utils.AppConstants
import top.umair.tecjaunttask.utils.getfireBaseStorageRef

object FireStoreManager {


    suspend fun uploadImage(imageUri: Uri?): String {
        val imageRef =
            getfireBaseStorageRef("${AppConstants.projectImages}/image${imageUri?.lastPathSegment}")
        if (imageUri != null) {
            imageRef.putFile(imageUri).await()
        }
        return imageRef.downloadUrl.await().toString()
    }


    fun innovatorProfile(pojo: InnovatortEntity, ref: String, function: (Boolean) -> Unit) {

        val documentReference =
            FirebaseFirestore.getInstance().collection(ref).document()
        val localId = documentReference.id
        pojo.documentId = localId
        documentReference.set(pojo)
            .addOnSuccessListener {

                function(true)
                Log.d("TAG", "addData: Success")
            }
            .addOnFailureListener {
                function(false)

                Log.e("TAG", "addData: Error", it)
            }
    }

//    inline fun <reified T : Any> getListOfData(collectionName: String): Flow<List<T>> = flow {
//        val list: MutableList<T> = mutableListOf()
//
//        val querySnapshot = getFireStoreRef(collectionName).get().await()
//
//        for (document in querySnapshot) {
//            val item = document.toObject(T::class.java)
//            list.add(item)
//        }
//
//        emit(list)
//    }.catch { exception ->
//        Log.d("TAG", "getFirestoreData: ${exception.message}")
//        emit(mutableListOf<T>())
//    }.flowOn(Dispatchers.IO)



}
