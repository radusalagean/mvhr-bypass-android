package com.radusalagean.mvhrbypass.databinding.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.slider.Slider

object SliderBindingAdapter {

    @JvmStatic @InverseBindingAdapter(attribute = "android:value")
    fun getValue(slider: Slider): Float {
        return slider.value
    }

    @JvmStatic @InverseBindingAdapter(attribute = "android:value")
    fun getValueInt(slider: Slider): Int {
        return slider.value.toInt()
    }

    @JvmStatic @BindingAdapter("android:valueAttrChanged")
    fun setListeners(slider: Slider, attrChange: InverseBindingListener) {
        slider.addOnChangeListener { slider, value, fromUser ->
            attrChange.onChange()
        }
    }
}