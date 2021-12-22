package com.privateproject.jetpacklearning.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.privateproject.jetpacklearning.local_storage.database.DogDatabase
import com.privateproject.jetpacklearning.local_storage.sharedprefrences.SharedPreferencesHelper
import com.privateproject.jetpacklearning.model.DogBread
import com.privateproject.jetpacklearning.server.DogsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(getApplication())

    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L // 5 min in nano sec

    private val dogsService = DogsApiService()

    private val disposable =
        CompositeDisposable()
    // retrieve or observe the observable that api gives us and not worry about get rid of it, handle memory leak

    val dogs = MutableLiveData<List<DogBread>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        val updateTime = prefHelper.getUpdateTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        }else {
            fetchFromTheRemote()
        }
    }

    fun refreshByPassCache() {
        fetchFromTheRemote()
    }


    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val list = DogDatabase(getApplication()).dogDao().getAll()
            dogRetrieve(list)
            Toast.makeText(getApplication(), "Dogs retrieve from DB", Toast.LENGTH_SHORT).show()
        }

    }

    private fun fetchFromTheRemote() {

        loading.value = true

        disposable.add(
            dogsService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<DogBread>>() {
                    override fun onSuccess(dogList: List<DogBread>) {
                        storeDogsLocally(dogList)
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        ) // separate thread and pass to bg thread
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


    private fun storeDogsLocally(dogList: List<DogBread>) {

        // do the job in the background and when it finishes it returns to main thread
        launch {
            val dao = DogDatabase(getApplication()).dogDao()
            dao.deleteAll()

            val result = dao.insertAll(*dogList.toTypedArray())

            var i = 0
            while (i < dogList.size) {
                dogList[i].uuid = result[i].toInt()
                ++i
            }

            dogRetrieve(dogList)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    private fun dogRetrieve(dogList: List<DogBread>) {
        dogs.value = dogList
        loading.value = false
        dogsLoadError.value = false
    }
}