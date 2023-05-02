package top.umair.tecjaunttask.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


fun getFireBaseAuth(): FirebaseAuth {
    return Firebase.auth
}

fun getFireStoreInstance(): FirebaseFirestore {
    return FirebaseFirestore.getInstance()
}

fun getFireStoreRef(collection: String): CollectionReference {
    return FirebaseFirestore.getInstance().collection(collection)

}


fun getfireBaseStorageRef(path: String): StorageReference {

    return FirebaseStorage.getInstance().reference.child("images").child(path)
}