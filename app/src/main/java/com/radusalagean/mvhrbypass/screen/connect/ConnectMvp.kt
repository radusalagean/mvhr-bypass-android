package com.radusalagean.mvhrbypass.screen.connect

import com.radusalagean.mvhrbypass.generic.mvp.BaseMvp

interface ConnectMvp {
    interface Model : BaseMvp.Model<ConnectViewModel> {

    }

    interface View : BaseMvp.View {

    }

    interface Presenter : BaseMvp.Presenter<View> {

    }
}