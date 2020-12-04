package com.radusalagean.mvhrbypass.generic.mvp

import androidx.fragment.app.Fragment
import com.radusalagean.mvhrbypass.generic.viewmodel.BaseViewModel

interface BaseMvp {

    interface Model<T : BaseViewModel> {
        var viewModel: T
        fun initViewModel(fragment: Fragment, viewModelClass: Class<T>)
    }

    interface View {
        fun setRefreshingIndicator(refreshing: Boolean)
        fun popBackStack()
        fun removeFragment()
    }

    interface Presenter<T : View> {
        var view: T?
        var refreshing: Boolean
        fun dispose()
        fun initViewModel(fragment: Fragment)
    }
}