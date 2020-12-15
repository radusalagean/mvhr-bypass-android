package com.radusalagean.mvhrbypass.generic.activity

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.radusalagean.mvhrbypass.generic.fragment.BaseFragment
import com.radusalagean.mvhrbypass.infobar.*
import com.radusalagean.mvhrbypass.screen.connect.ConnectFragment
import com.radusalagean.mvhrbypass.screen.main.MainFragment
import org.koin.androidx.scope.ScopeActivity
import timber.log.Timber

abstract class BaseActivity : ScopeActivity(), ActivityContract, InfoBarContract {

    private val logTag: String = javaClass.simpleName
    private val infoBarManager: InfoBarManager by inject()

    // Lifecycle callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(logTag).v("-A-> onCreate($savedInstanceState)")
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        Timber.tag(logTag).v("-A-> onStart()")
        super.onStart()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Timber.tag(logTag).v("-A-> onRestoreInstanceState($savedInstanceState)")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        Timber.tag(logTag).v("-A-> onResume()")
        super.onResume()
        infoBarManager.resume(this)
    }

    override fun onPause() {
        Timber.tag(logTag).v("-A-> onPause()")
        infoBarManager.pause()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Timber.tag(logTag).v("-A-> onSaveInstanceState($outState)")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Timber.tag(logTag).v("-A-> onStop()")
        super.onStop()
    }

    override fun onDestroy() {
        Timber.tag(logTag).v("-A-> onDestroy()")
        super.onDestroy()
    }

    // Other activity callbacks

    /**
     * First, pass the event to the current fragment
     * If it's not handled there, let the activity handle it
     */
    override fun onBackPressed() {
        getCurrentFragment()?.let {
            if (!it.onBackPressed()) {
                super.onBackPressed()
            }
        } ?: super.onBackPressed()
    }

    // Base methods

    /**
     * Adds a default fragment if no fragment is present for the specified container
     */
    protected fun addDefaultFragmentIfNecessary() {
        val currentFragment = getCurrentFragment()
        if (currentFragment == null) {
            val defaultFragment = getDefaultFragment()
            Timber.d("No fragment was previously attached, attaching %s as starting point", defaultFragment)
            showFragment(defaultFragment, false, null)
        }
    }

    fun getCurrentFragment() =
        supportFragmentManager.findFragmentById(getFragmentContainerId()) as BaseFragment?

    /**
     * Show a fragment
     */
    protected fun <T : BaseFragment> showFragment(
        fragment: T,
        addToBackStack: Boolean = true,
        backStackStateName: String? = null
    ) {
        // Make sure the fragment is not already in the foreground
        with(getCurrentFragment()) {
            if (this != null && javaClass == fragment.javaClass) {
                Timber.d("Attempting to open an unnecessary fragment, skipping request!")
                return
            }
        }
        supportFragmentManager.beginTransaction().apply {
            replace(getFragmentContainerId(), fragment, fragment.javaClass.name)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            if (addToBackStack) {
                addToBackStack(backStackStateName)
            }
        }.commit()
    }

    // Activity contract implementation

    override fun showInfoMessage(message: Int, vararg args: Any?) {
        enqueueMessage(TYPE_INFO, message, *args)
    }

    override fun showWarningMessage(message: Int, vararg args: Any?) {
        enqueueMessage(TYPE_WARN, message, *args)
    }

    override fun showErrorMessage(message: Int, vararg args: Any?) {
        enqueueMessage(TYPE_ERROR, message, *args)
    }

    override fun popAllFragments() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun showConnectScreen() {
        showFragment(ConnectFragment.newInstance())
    }

    override fun showMainScreen(socketAddress: String) {
        showFragment(MainFragment.newInstance(socketAddress))
    }

    // InfoBar Contract

    /**
     * Call in order to display an [InfoBar] message immediately
     */
    override fun showInfoBarNow(
        infoBarConfiguration: InfoBarConfiguration,
        callback: BaseTransientBottomBar.BaseCallback<InfoBar>
    ) {
        getCurrentFragment()?.let {
            InfoBar.make(
                it.getInfoBarContainer(),
                infoBarConfiguration.message,
                infoBarConfiguration.type
            ).addCallback(callback).show()
        }
    }

    // Abstract methods

    /**
     * Override to specify the default fragment to be added with the [addDefaultFragmentIfNecessary] method
     */
    protected abstract fun getDefaultFragment(): BaseFragment

    // Private

    /**
     * Enqueue the [InfoBar] message
     */
    private fun enqueueMessage(type: Int, message: Int, vararg args: Any?) {
        val string = if (args.isEmpty()) getString(message) else getString(message, *args)
        getCurrentFragment()?.let {
            infoBarManager.enqueueMessage(InfoBarConfiguration(string, type))
        }
    }

}