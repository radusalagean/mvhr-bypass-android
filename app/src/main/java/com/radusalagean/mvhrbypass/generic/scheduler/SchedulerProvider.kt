package com.radusalagean.mvhrbypass.generic.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface BaseSchedulerProvider {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun main(): Scheduler
}

class SchedulerProvider : BaseSchedulerProvider {
    override fun computation(): Scheduler = Schedulers.computation()
    override fun main(): Scheduler = AndroidSchedulers.mainThread()
    override fun io(): Scheduler = Schedulers.io()
}