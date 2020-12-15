package com.radusalagean.mvhrbypass.databinding.adapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.radusalagean.mvhrbypass.screen.main.TempTableEntryViewModel

object TextViewBindingAdapter {

    @BindingAdapter("stringResId")
    @JvmStatic fun setStringResId(textView: TextView, stringResId: Int) {
        if (stringResId > 0)
            textView.setText(stringResId)
        else
            textView.text = null
    }

    @BindingAdapter("tempTableEntry")
    @JvmStatic fun setTempTableEntry(textView: TextView, model: TempTableEntryViewModel?) {
        if (model == null) return
        val text = textView.context.getString(model.tempStringResId, model.temp)
        textView.text = text
        val color = ContextCompat.getColor(textView.context, model.tempColor)
        textView.setTextColor(color)
    }
}