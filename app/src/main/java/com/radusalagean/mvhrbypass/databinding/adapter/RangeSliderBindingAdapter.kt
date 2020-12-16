package com.radusalagean.mvhrbypass.databinding.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.LiveData
import com.google.android.material.slider.RangeSlider

object RangeSliderBindingAdapter {

    @JvmStatic @BindingAdapter("values")
    fun setValues(rangeSlider: RangeSlider, values: LiveData<Pair<Int, Int>?>) {
        values.value?.let {
            rangeSlider.values = listOf(it.first.toFloat(), it.second.toFloat())
        }
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

    @JvmStatic @BindingAdapter("app:minSeparationValue")
    fun setMinSeparationValue(rangeSlider: RangeSlider, minSeparationValue: Int) {
        rangeSlider.setMinSeparationValue(minSeparationValue.toFloat())
    }

    @JvmStatic @BindingAdapter("onChangeListener")
    fun setOnChangeListener(rangeSlider: RangeSlider, callback: Runnable) {
        rangeSlider.addOnChangeListener { slider, value, fromUser ->
            if (fromUser) callback.run()
        }
    }
}