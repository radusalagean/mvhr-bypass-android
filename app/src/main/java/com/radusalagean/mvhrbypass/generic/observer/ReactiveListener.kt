package com.radusalagean.mvhrbypass.generic.observer

interface ReactiveListener {
    fun onError(t: Throwable)
    fun onComplete()
}