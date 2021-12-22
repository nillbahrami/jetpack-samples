package com.privateproject.jetpacklearning.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privateproject.jetpacklearning.local_storage.database.DogDatabase
import com.privateproject.jetpacklearning.model.DogBread
import kotlinx.coroutines.launch
import java.util.*

class DetailViewModel(application: Application): BaseViewModel(application) {

    val dogLiveData = MutableLiveData<DogBread>()

    fun fetch(uuid: Int) {

        launch {
            val dog = DogDatabase(getApplication()).dogDao().getById(uuid)
            dogLiveData.value = dog
        }
    }

}