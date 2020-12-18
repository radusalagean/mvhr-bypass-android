package com.radusalagean.mvhrbypass.generic.viewmodel

import androidx.lifecycle.ViewModel
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared()")
    }

    open fun loadData() {}
}