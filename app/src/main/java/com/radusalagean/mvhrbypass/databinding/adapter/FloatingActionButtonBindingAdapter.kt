package com.radusalagean.mvhrbypass.databinding.adapter

import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

object FloatingActionButtonBindingAdapter {

    @JvmStatic @BindingAdapter("android:visibility")
    fun setVisibility(fab: FloatingActionButton, visible: Boolean) {
        if (visible) fab.show() else fab.hide()
    }
}