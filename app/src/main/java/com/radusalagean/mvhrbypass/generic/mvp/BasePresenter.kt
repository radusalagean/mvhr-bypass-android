package com.radusalagean.mvhrbypass.generic.mvp

import com.radusalagean.mvhrbypass.generic.activity.ActivityContract
import com.radusalagean.mvhrbypass.generic.observer.ReactiveListener
import com.radusalagean.mvhrbypass.generic.scheduler.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

abstract class BasePresenter<T : BaseMvp.View, S : BaseMvp.Model<*>>(
    protected val model: S,
    protected val activityContract: ActivityContract,
    protected val schedulerProvider: BaseSchedulerProvider
) : BaseMvp.Presenter<T>, ReactiveListener {

    // Mvp Implementation

    override var view: T? = null

    override var refreshing: Boolean = false
        set(value) {
            // Update the main progress indicator from the view
            view?.setRefreshingIndicator(value)
            // Assign the value to the field
            field = value
        }

    protected val disposer = CompositeDisposable()

    override fun dispose() {
        disposer.clear()
    }

    // Reactive listener

    override fun onError(t: Throwable) {
        Timber.e(t)
        refreshing = false
    }

    override fun onComplete() {
        refreshing = false
    }
}