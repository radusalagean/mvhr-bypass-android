package com.radusalagean.mvhrbypass.databinding.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.LiveData
import com.google.android.material.slider.RangeSlider

object RangeSliderBindingAdapter {

    @JvmStatic @BindingAdapter("values")
    fun setValues(rangeSlider: RangeSlider, values: LiveData<Pair<Int, Int>>) {
        rangeSlider.values = listOf(values.value!!.first.toFloat(), values.value!!.second.toFloat())
    }

    @JvmStatic @InverseBindingAdapter(attribute = "app:values")
    fun getValues(rangeSlider: RangeSlider): Pair<Int, Int> {
        return rangeSlider.values[0].toInt() to rangeSlider.values[1].toInt()
    }

    @JvmStatic @BindingAdapter("app:valuesAttrChanged")
    fun setListeners(rangeSlider: RangeSlider, attrChange: InverseBindingListener) {
        rangeSlider.addOnChangeListener { slider, value, fromUser ->
            attrChange.onChange()
        }
    }
}