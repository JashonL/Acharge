package com.dxm.dxmcharge.ui.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.dxm.dxmcharge.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LuanchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luanch)

        lifecycleScope.launch {
            delay(2000)
            WelcomeActivity.start(this@LuanchActivity)
        }

    }
}