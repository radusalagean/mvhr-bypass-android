package com.radusalagean.mvhrbypass.screen.main

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

data class TempTableEntryViewModel(
        val temp: Float,
        @StringRes val tempStringResId: Int,
        @ColorRes val tempColor: Int
)
