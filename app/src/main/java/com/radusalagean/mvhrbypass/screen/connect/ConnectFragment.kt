package com.radusalagean.mvhrbypass.screen.connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.radusalagean.mvhrbypass.R
import com.radusalagean.mvhrbypass.generic.activity.ActivityContract
import com.radusalagean.mvhrbypass.generic.fragment.BaseFragment
import com.radusalagean.mvhrbypass.generic.mvp.BaseMvp
import kotlinx.android.synthetic.main.fragment_connect.*

class ConnectFragment : BaseFragment(), ConnectMvp.View {

    val presenter: ConnectMvp.Presenter by inject()
    val activityContract: ActivityContract by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_connect, container, false)
    }

    // BaseFragment implementation

    override fun initViews() {
        activityContract.getToolbar()?.title = getString(R.string.connect_fragment_title)
    }

    override fun disposeViews() {
        activityContract.getToolbar()?.title = null
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

    override fun getInfoBarContainer(): ViewGroup = connect_fragment_root_view

    companion object {
        fun newInstance() = ConnectFragment()
    }
}