package com.radusalagean.mvhrbypass.generic.activity

import androidx.annotation.IdRes

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
    fun showMainScreen(socketAddress: String)
}