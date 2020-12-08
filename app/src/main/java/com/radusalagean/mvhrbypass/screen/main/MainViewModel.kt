package com.radusalagean.mvhrbypass.screen.main

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import com.radusalagean.mvhrbypass.R
import com.radusalagean.mvhrbypass.generic.activity.ActivityContract
import com.radusalagean.mvhrbypass.generic.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {

    val hrModeText = MutableLiveData<String>()
    val hrModeBackground = MutableLiveData<Drawable>()
    val extEv = MutableLiveData("24.2")
    val extAd = MutableLiveData("24.2")
    val intAd = MutableLiveData("24.2")
    val intEv = MutableLiveData("24.2")

    fun onModeClick(activityContract: ActivityContract) {
        activityContract.showInfoMessage(R.string.onClickStr)
    }

    fun onModeLongClick(activityContract: ActivityContract) {
        activityContract.showWarningMessage(R.string.onLongClickStr)
    }
}