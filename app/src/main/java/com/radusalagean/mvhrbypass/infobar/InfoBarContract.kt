package com.radusalagean.mvhrbypass.infobar

import com.google.android.material.snackbar.BaseTransientBottomBar

interface InfoBarContract {

    fun showInfoBarNow(
        infoBarConfiguration: InfoBarConfiguration,
        callback: BaseTransientBottomBar.BaseCallback<InfoBar>
    )
}