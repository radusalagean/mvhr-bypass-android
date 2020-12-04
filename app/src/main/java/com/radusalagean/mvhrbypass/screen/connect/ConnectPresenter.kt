package com.radusalagean.mvhrbypass.screen.connect

import androidx.fragment.app.Fragment
import com.radusalagean.mvhrbypass.R
import com.radusalagean.mvhrbypass.generic.activity.ActivityContract
import com.radusalagean.mvhrbypass.generic.mvp.BasePresenter
import com.radusalagean.mvhrbypass.generic.scheduler.BaseSchedulerProvider

class ConnectPresenter(
    model: ConnectMvp.Model,
    activityContract: ActivityContract,
    schedulerProvider: BaseSchedulerProvider
) : BasePresenter<ConnectMvp.View, ConnectMvp.Model>(model, activityContract, schedulerProvider),
    ConnectMvp.Presenter {

    override fun initViewModel(fragment: Fragment) {
        model.initViewModel(fragment, ConnectViewModel::class.java)
    }
}