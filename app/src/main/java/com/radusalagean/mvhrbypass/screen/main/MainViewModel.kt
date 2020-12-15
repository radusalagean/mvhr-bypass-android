package com.radusalagean.mvhrbypass.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.radusalagean.mvhrbypass.R
import com.radusalagean.mvhrbypass.generic.activity.ActivityContract
import com.radusalagean.mvhrbypass.generic.viewmodel.BaseViewModel
import com.radusalagean.mvhrbypass.network.SocketManager
import com.radusalagean.mvhrbypass.network.SocketSubscriber
import com.radusalagean.mvhrbypass.network.model.InitData
import com.radusalagean.mvhrbypass.network.model.State
import com.radusalagean.mvhrbypass.network.model.Temperatures
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel(private val savedStateHandle: SavedStateHandle) : BaseViewModel(),
        KoinComponent, SocketSubscriber {
    private val _refreshing = MutableLiveData(true)
    val refreshing: LiveData<Boolean>
        get() = _refreshing

    val hrModeTextResId = MutableLiveData<Int>()
    val hrModeBackgroundColorResId = MutableLiveData<Int>()
    val extEv = MutableLiveData<TempTableEntryViewModel>()
    val extAd = MutableLiveData<TempTableEntryViewModel>()
    val intAd = MutableLiveData<TempTableEntryViewModel>()
    val intEv = MutableLiveData<TempTableEntryViewModel>()
    val hysteresis = MutableLiveData<Float>()
    val intEvMin = MutableLiveData<Int>()
    val extAdPair = MutableLiveData<Pair<Int, Int>>()

    private val socketManager: SocketManager by inject()

    init {
        subscribe()
    }

    override fun onCleared() {
        unsubscribe()
        super.onCleared()
    }

    fun onModeClick(activityContract: ActivityContract) {
        activityContract.showInfoMessage(R.string.onClickStr)
    }

    fun onModeLongClick(activityContract: ActivityContract) {
        activityContract.showWarningMessage(R.string.onLongClickStr)
    }

    fun connect() {
        val address: String = savedStateHandle[MainFragment.ARG_SOCKET_ADDRESS]!!
        socketManager.connect(address)
    }

    private fun subscribe() {
        socketManager.subscribe(this)
    }

    private fun unsubscribe() {
        socketManager.unsubscribe(this)
    }

    private fun assignState(state: State) {
        hrModeTextResId.value = if (state.hrModeAuto)
            R.string.mode_auto else R.string.mode_manual
        hrModeBackgroundColorResId.value = if (state.hrDisabled)
            R.color.color_cell_hr_disabled else R.color.color_cell_hr_enabled
        intEvMin.value = state.intEvMin
        extAdPair.value = state.extAdMin to state.extAdMax
        hysteresis.value = state.hysteresis
    }

    private fun assignTemperatures(temperatures: Temperatures) {
        extEv.value = buildTempTableEntryViewModel(temperatures.extEv)
        extAd.value = buildTempTableEntryViewModel(temperatures.extAd)
        intAd.value = buildTempTableEntryViewModel(temperatures.intAd)
        intEv.value = buildTempTableEntryViewModel(temperatures.intEv)
    }

    private fun buildTempTableEntryViewModel(temp: Float): TempTableEntryViewModel {
        return TempTableEntryViewModel(
                temp,
                if (temp == TEMP_ERROR) R.string.table_temp_error else R.string.table_temp_format,
                if (temp == TEMP_ERROR) R.color.color_temp_error else R.color.color_temp_ok
        )
    }

    override fun onInitDataReceived(initData: InitData) {
        assignState(initData.state)
        assignTemperatures(initData.temperatures)
        _refreshing.value = false
    }

    companion object {
        const val TEMP_ERROR = -100.0f
    }
}