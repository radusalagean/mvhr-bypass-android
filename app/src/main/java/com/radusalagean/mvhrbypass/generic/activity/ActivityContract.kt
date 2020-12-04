package com.radusalagean.mvhrbypass.generic.activity

import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar

/**
 * The contract that opens Activity-specific functionality to other components
 */
interface ActivityContract {

    /**
     * Show an on-screen message through the [InfoBar] ([TYPE_INFO] level)
     */
    fun showInfoMessage(message: Int, vararg args: Any? = emptyArray())

    /**
     * Show an on-screen message through the [InfoBar] ([TYPE_WARN] level)
     */
    fun showWarningMessage(message: Int, vararg args: Any? = emptyArray())

    /**
     * Show an on-screen message through the [InfoBar] ([TYPE_ERROR] level)
     */
    fun showErrorMessage(message: Int, vararg args: Any? = emptyArray())

    /**
     * Get the main container for Fragments from a specific Activity
     */
    @IdRes
    fun getFragmentContainerId(): Int

    /**
     * Pop all fragments from the back stack
     */
    fun popAllFragments()

    // Show specific screens

    fun showConnectScreen()
    fun showMainScreen()

    /**
     * Called when the app successfully connected to the MVHR system
     */
    fun handleSuccessfulConnect()

    /**
     * Called when the app failed to connect to the MVHR system
     */
    fun handleFailedConnect(t: Throwable?)

    /**
     * Called when the disconnect button is pressed
     */
    fun handleDisconnect()

    fun getToolbar(): ActionBar?
}