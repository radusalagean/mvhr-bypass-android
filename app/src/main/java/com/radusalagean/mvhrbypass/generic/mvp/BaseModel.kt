package com.radusalagean.mvhrbypass.generic.mvp

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.radusalagean.mvhrbypass.generic.viewmodel.BaseViewModel

abstract class BaseModel<T : BaseViewModel> : BaseMvp.Model<T> {
    override lateinit var viewModel: T

    /**
     * Call from the Presenter when the corresponding Fragment is first created
     */
    override fun initViewModel(fragment: Fragment, viewModelClass: Class<T>) {
        viewModel = ViewModelProvider(fragment)[viewModelClass]
    }
}