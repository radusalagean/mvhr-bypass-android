package com.radusalagean.mvhrbypass.screen.main

import androidx.fragment.app.Fragment
import com.radusalagean.mvhrbypass.generic.activity.ActivityContract
import com.radusalagean.mvhrbypass.generic.mvp.BasePresenter
import com.radusalagean.mvhrbypass.generic.scheduler.BaseSchedulerProvider

class MainPresenter(
    model: MainMvp.Model,
    activityContract: ActivityContract,
    schedulerProvider: BaseSchedulerProvider
) : BasePresenter<MainMvp.View, MainMvp.Model>(model, activityContract, schedulerProvider), MainMvp.Presenter {

    override fun initViewModel(fragment: Fragment) {
        model.initViewModel(fragment, MainViewModel::class.java)
    }
}