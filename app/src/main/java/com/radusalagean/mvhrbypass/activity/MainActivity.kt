package com.radusalagean.mvhrbypass.activity

import android.os.Bundle
import com.radusalagean.mvhrbypass.R
import com.radusalagean.mvhrbypass.generic.activity.BaseActivity
import com.radusalagean.mvhrbypass.screen.connect.ConnectFragment

class MainActivity : BaseActivity() {

    // Lifecycle callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addDefaultFragmentIfNecessary()
    }

    // Activity contract implementation

    override fun getFragmentContainerId() = R.id.fragment_container

    // BaseActivity implementation

    override fun getDefaultFragment() = ConnectFragment.newInstance()
}