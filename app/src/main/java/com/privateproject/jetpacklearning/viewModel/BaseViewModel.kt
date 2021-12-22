package com.privateproject.jetpacklearning.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application): AndroidViewModel(application), CoroutineScope {

    // app context to have access to DB

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main // we have a job that is running and then when its finished return to it main thread

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}