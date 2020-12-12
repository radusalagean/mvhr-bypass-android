package com.radusalagean.mvhrbypass.screen.main

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import com.radusalagean.mvhrbypass.R
import com.radusalagean.mvhrbypass.generic.activity.ActivityContract
import com.radusalagean.mvhrbypass.generic.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {

    val hrModeText = MutableLiveData<String>()
    val hrModeBackground = MutableLiveData<Drawable>()
    val extEv = MutableLiveData(16.2f)
    val extAd = MutableLiveData(10.0f)
    val intAd = MutableLiveData(22.1f)
    val intEv = MutableLiveData(28.0f)
    val extEvFormatted = MutableLiveData("16.2")
    val extAdFormatted = MutableLiveData("10.0")
    val intAdFormatted = MutableLiveData("22.1")
    val intEvFormatted = MutableLiveData("28.0")
    val hysteresis = MutableLiveData(1.0f)
    val intEvLow = MutableLiveData(23)
    val extAdPair = MutableLiveData(11 to 24)

    fun onModeClick(activityContract: ActivityContract) {
        activityContract.showInfoMessage(R.string.onClickStr)
    }

    fun onModeLongClick(activityContract: ActivityContract) {
        activityContract.showWarningMessage(R.string.onLongClickStr)
    }
}