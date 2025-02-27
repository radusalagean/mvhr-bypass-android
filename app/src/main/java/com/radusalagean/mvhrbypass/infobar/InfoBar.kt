package com.radusalagean.mvhrbypass.infobar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.radusalagean.mvhrbypass.R
import com.radusalagean.mvhrbypass.extension.findSuitableParent
import com.radusalagean.mvhrbypass.view.infobar.InfoBarView
import kotlinx.android.synthetic.main.layout_info_bar.view.*

const val TYPE_ERROR = 1
const val TYPE_WARN = 2
const val TYPE_INFO = 3

/**
 * A custom info bar, similar in behavior to the Android SnackBar
 */
class InfoBar(
    parent: ViewGroup,
    content: InfoBarView
) : BaseTransientBottomBar<InfoBar>(parent, content, content) {

    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        getView().setPadding(0, 0, 0, 0)
    }

    companion object {
        fun make(view: View, string: String, type: Int): InfoBar {

            // First we find a suitable parent for our custom view
            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )

            // We inflate our custom view
            val customView = LayoutInflater.from(view.context)
                .inflate(R.layout.view_info_bar, parent, false) as InfoBarView

            // Apply background
            val color = when (type) {
                TYPE_ERROR -> R.color.colorError
                TYPE_WARN -> R.color.colorWarn
                TYPE_INFO -> R.color.colorInfo
                else -> TYPE_INFO
            }
            if (color != 0) {
                customView.background =
                    ResourcesCompat.getDrawable(customView.resources, color, null)
            }

            // Set text
            customView.info_bar_text_view.text = string

            // We create and return our InfoBar
            return InfoBar(
                parent,
                customView
            )
        }
    }
}
