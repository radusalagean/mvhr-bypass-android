package com.radusalagean.mvhrbypass.screen.connect

import androidx.lifecycle.MutableLiveData
import com.radusalagean.mvhrbypass.generic.activity.ActivityContract
import com.radusalagean.mvhrbypass.generic.viewmodel.BaseViewModel

class ConnectViewModel : BaseViewModel() {

    val address = MutableLiveData<String>("ws://192.168.0.175:25484")

    fun onConnectClicked(activityContract: ActivityContract) {
        activityContract.showMainScreen(address.value!!)
    }
}