package com.dxm.dxmcharge.ui.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.dxm.dxmcharge.App
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.ui.account.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LuanchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luanch)
        lifecycleScope.launch {
            delay(2000)
            toNext()
        }

    }


    private fun toNext() {
        if (App.instance().isIsFirst()) {
            LoginActivity.start(this@LuanchActivity)
        } else {
            WelcomeActivity.start(this@LuanchActivity)
        }
    }
}