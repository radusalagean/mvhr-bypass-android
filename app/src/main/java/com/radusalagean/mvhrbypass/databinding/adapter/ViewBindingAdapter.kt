package com.radusalagean.mvhrbypass.databinding.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

object ViewBindingAdapter {

    @BindingAdapter("backgroundColorResId")
    @JvmStatic fun setBackgroundColorResId(view: View, backgroundColorResId: Int) {
        if (backgroundColorResId > 0) {
            val color = ContextCompat.getColor(view.context, backgroundColorResId)
            view.setBackgroundColor(color)
        } else {
            view.background = null
        }
    }
}