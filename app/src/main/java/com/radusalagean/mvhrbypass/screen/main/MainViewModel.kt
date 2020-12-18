package com.radusalagean.mvhrbypass.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.radusalagean.mvhrbypass.R
import com.radusalagean.mvhrbypass.generic.viewmodel.BaseViewModel
import com.radusalagean.mvhrbypass.network.SocketManager
import com.radusalagean.mvhrbypass.network.SocketSubscriber
import com.radusalagean.mvhrbypass.network.model.InitData
import com.radusalagean.mvhrbypass.network.model.State
import com.radusalagean.mvhrbypass.network.model.Temperatures
import com.radusalagean.mvhrbypass.persistence.sharedprefs.SharedPreferencesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class MainViewModel(private val savedStateHandle: SavedStateHandle) : BaseViewModel(),
        KoinComponent, SocketSubscriber {

    private val sharedPreferencesRepository by inject<SharedPreferencesRepository>()

    private val _refreshing = MutableLiveData(true)
    val refreshing: LiveData<Boolean>
        get() = _refreshing

    val hrModeAuto = MutableLiveData<Boolean>()
    val hrDisabled = MutableLiveData<Boolean>()
    val hrModeTextResId = MutableLiveData<Int>()
    val hrModeBackgroundColorResId = MutableLiveData<Int>()
    val extEv = MutableLiveData<TempTableEntryViewModel>()
    val extAd = MutableLiveData<TempTableEntryViewModel>()
    val intAd = MutableLiveData<TempTableEntryViewModel>()
    val intEv = MutableLiveData<TempTableEntryViewModel>()

    val editMode = MutableLiveData(false)
    var hysteresisOriginal: Float = 0.0f
    val hysteresis = MutableLiveData<Float>()
    var intEvMinOriginal: Int = 0
    val intEvMin = MutableLiveData<Int>()
    var extAdPairOriginal: Pair<Int, Int>? = null
    val extAdPair = MutableLiveData<Pair<Int, Int>>()

    private val socketManager: SocketManager by inject()

    init {
        subscribe()
    }

    override fun onCleared() {
        unsubscribe()
        super.onCleared()
    }

    fun onModeClick() {
        socketManager.run {
            if (hrModeAuto.value == true) requestHrModeManual() else requestHrModeAuto()
        }
    }

    fun onModeLongClick() {
        socketManager.run {
            if (hrModeAuto.value == false) {
                if (hrDisabled.value == true) requestEnableHr() else requestDisableHr()
            }
        }
    }

    fun onSliderChanged() {
        refreshEditMode()
    }

    fun onSaveClicked() {
        socketManager.requestApplyStateTemperatures(
            intEvMin = intEvMin.value!!,
            extAdMin = extAdPair.value!!.first,
            extAdMax = extAdPair.value!!.second,
            hysteresis = hysteresis.value!!
        )
        editMode.value = false
    }

    fun onBackPressed(): Boolean {
        return if (editMode.value == true) {
            syncEditableFieldsWithOriginal()
            refreshEditMode()
            true
        } else false
    }

    fun connect() {
        val address: String = savedStateHandle[MainFragment.ARG_SOCKET_ADDRESS]!!
        socketManager.connect(address)
    }

    fun closeConnection() {
        socketManager.closeConnection()
    }

    private fun subscribe() {
        socketManager.subscribe(this)
    }

    private fun unsubscribe() {
        socketManager.unsubscribe(this)
    }

    private fun syncEditableFieldsWithOriginal() {
        intEvMin.value = intEvMinOriginal
        extAdPair.value = extAdPairOriginal
        hysteresis.value = hysteresisOriginal
    }

    private fun assignState(state: State) {
        hrModeAuto.value = state.hrModeAuto
        hrDisabled.value = state.hrDisabled
        hrModeTextResId.value = if (state.hrModeAuto == true)
            R.string.mode_auto else R.string.mode_manual
        hrModeBackgroundColorResId.value = if (state.hrDisabled == true)
            R.color.color_cell_hr_disabled else R.color.color_cell_hr_enabled
        intEvMinOriginal = state.intEvMin
        extAdPairOriginal = state.extAdMin to state.extAdMax
        hysteresisOriginal = state.hysteresis
        if (editMode.value == false) {
            syncEditableFieldsWithOriginal()
        }
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

    private fun refreshEditMode() {
        editMode.value = isInEditMode()
    }

    private fun isInEditMode(): Boolean {
        val originalHash = Objects.hash(hysteresisOriginal, intEvMinOriginal, extAdPairOriginal)
        val currentHash = Objects.hash(hysteresis.value, intEvMin.value, extAdPair.value)
        return originalHash != currentHash
    }

    override fun onInitDataReceived(initData: InitData) {
        assignState(initData.state)
        assignTemperatures(initData.temperatures)
        _refreshing.value = false
    }

    override fun onStateReceived(state: State) {
        assignState(state)
    }

    override fun onTemperaturesReceived(temperatures: Temperatures) {
        assignTemperatures(temperatures)
    }

    override fun onConnectionOpen() {
        sharedPreferencesRepository.setLastValidAddress(
            savedStateHandle[MainFragment.ARG_SOCKET_ADDRESS]!!
        )
    }

    companion object {
        const val TEMP_ERROR = -100.0f
    }
}