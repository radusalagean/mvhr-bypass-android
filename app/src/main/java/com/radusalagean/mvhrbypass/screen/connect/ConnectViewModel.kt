package com.radusalagean.mvhrbypass.screen.connect

import androidx.lifecycle.MutableLiveData
import com.radusalagean.mvhrbypass.BuildConfig
import com.radusalagean.mvhrbypass.generic.activity.ActivityContract
import com.radusalagean.mvhrbypass.generic.viewmodel.BaseViewModel
import com.radusalagean.mvhrbypass.persistence.sharedprefs.SharedPreferencesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ConnectViewModel : BaseViewModel(), KoinComponent {

    val versionName: String = BuildConfig.VERSION_NAME

    private val sharedPreferencesRepository by inject<SharedPreferencesRepository>()
    val address = MutableLiveData<String>()

    override fun loadData() {
        loadLastValidAddress()
    }

    private fun loadLastValidAddress() {
        address.value = sharedPreferencesRepository.getLastValidAddress()
    }

    fun onConnectClicked(activityContract: ActivityContract) {
        if (!address.value.isNullOrBlank())
            activityContract.showMainScreen(address.value!!)
    }
}