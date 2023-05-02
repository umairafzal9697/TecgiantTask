package top.umair.tecjaunttask.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import top.umair.tecjaunttask.data.firestore.FireStoreManager
import top.umair.tecjaunttask.models.InnovatortEntity
import top.umair.tecjaunttask.utils.AppConstants

@HiltViewModel
class InnovatorViewModel:ViewModel() {


    fun addInnovatorProfile(innovatorPojo: InnovatortEntity, imgUri: Uri, function: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val imageUrl = FireStoreManager.uploadImage(imgUri)
                innovatorPojo.profileImage = imageUrl
                FireStoreManager.innovatorProfile(innovatorPojo, AppConstants.innovators) {
                    function(it)

                }

            } catch (e: Exception) {
                Log.e("TAG", "Error uploading image", e)
            }
        }
    }

}