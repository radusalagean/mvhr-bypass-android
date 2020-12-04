package com.radusalagean.mvhrbypass.screen.main

import com.radusalagean.mvhrbypass.generic.mvp.BaseMvp

interface MainMvp {

    interface Model : BaseMvp.Model<MainViewModel> {

    }

    interface View : BaseMvp.View {

    }

    interface Presenter : BaseMvp.Presenter<View> {

    }
}