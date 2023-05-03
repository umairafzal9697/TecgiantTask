package top.umair.tecjaunttask.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import top.umair.tecjaunttask.data.repository.InnovatorRepository
import top.umair.tecjaunttask.models.InnovatorEntity
import top.umair.tecjaunttask.utils.AppConstants
import javax.inject.Inject

@HiltViewModel
class InnovatorViewModel @Inject constructor(
    private val repository: InnovatorRepository
) : ViewModel() {

    private val _innovatorListState = MutableStateFlow<List<InnovatorEntity>>(emptyList())
    val innovatorListState = _innovatorListState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAll().collect {
                _innovatorListState.value = it
                Log.d("meraData", ": $it")
            }
        }
    }





    fun addInnovatorProfile(innovator: InnovatorEntity, function: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                        repository.addInnovator(innovator,AppConstants.innovators){
                            function(it)
                        }




            } catch (e: Exception) {
                Log.e("TAG", "Error uploading image", e)
            }
        }
    }



    fun backupData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.backupData()
        }
    }

    fun restoreData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.restoreData()
        }
    }


}
