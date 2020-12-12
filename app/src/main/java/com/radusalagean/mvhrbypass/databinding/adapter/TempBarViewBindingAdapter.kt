package com.radusalagean.mvhrbypass.databinding.adapter

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.radusalagean.mvhrbypass.view.tempbar.TempBarView

object TempBarViewBindingAdapter {

    @BindingAdapter("valueLow")
    @JvmStatic fun setValueLow(tempBarView: TempBarView, valueLow: LiveData<Int>) {
        tempBarView.valueLow = valueLow.value!!
    }

    @JvmStatic @BindingAdapter("valueHigh")
    fun setValueHigh(tempBarView: TempBarView, valueHigh: LiveData<Int>) {
        tempBarView.valueHigh = valueHigh.value!!
    }

    @JvmStatic @BindingAdapter("hysteresis")
    fun setHysteresis(tempBarView: TempBarView, hysteresis: LiveData<Float>) {
        tempBarView.hysteresis = hysteresis.value!!
    }

    @JvmStatic @BindingAdapter("currentTemp")
    fun setCurrentTemp(tempBarView: TempBarView, currentTemp: LiveData<Float>) {
        tempBarView.currentTemp = currentTemp.value!!
    }
}