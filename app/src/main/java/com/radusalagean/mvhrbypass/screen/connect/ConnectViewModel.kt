package com.radusalagean.mvhrbypass.screen.connect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.radusalagean.mvhrbypass.generic.activity.ActivityContract
import com.radusalagean.mvhrbypass.generic.viewmodel.BaseViewModel

class ConnectViewModel : BaseViewModel() {
    private val _refreshing = MutableLiveData(false)
    val refreshing: LiveData<Boolean>
        get() = _refreshing

    fun onConnectClicked(activityContract: ActivityContract) {
        activityContract.showMainScreen()
    }
}