package com.radusalagean.mvhrbypass.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.radusalagean.mvhrbypass.R
import com.radusalagean.mvhrbypass.generic.activity.ActivityContract
import com.radusalagean.mvhrbypass.generic.fragment.BaseFragment
import com.radusalagean.mvhrbypass.generic.mvp.BaseMvp
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(), MainMvp.View {

    val presenter: MainMvp.Presenter by inject()
    val activityContract: ActivityContract by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun initViews() {
        
    }

    override fun disposeViews() {
        
    }

    override fun registerListeners() {
        
    }

    override fun unregisterListeners() {
        
    }

    override fun loadData() {
        
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : BaseMvp.View> getPresenter(): BaseMvp.Presenter<T> =
        presenter as BaseMvp.Presenter<T>

    override fun getInfoBarContainer(): ViewGroup = fragment_main_root_view

    companion object {
        fun newInstance() = MainFragment()
    }
}