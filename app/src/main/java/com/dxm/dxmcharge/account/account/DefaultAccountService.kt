package com.shuoxd.charge.service.account

import android.content.Context
import com.dxm.dxmcharge.account.BaseAccountService
import com.dxm.dxmcharge.foreground.Foreground
import com.dxm.dxmcharge.ui.account.login.LoginActivity

class DefaultAccountService : BaseAccountService() {

    override fun login(context: Context) {
        LoginActivity.startClearTask(context)
    }

    override fun tokenExpired() {
        logout()
        Foreground.instance().getTopActivity()?.also {
            login(it)
        }
    }
}