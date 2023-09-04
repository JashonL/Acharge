package com.dxm.dxmcharge.ui.start

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dxm.dxmcharge.App
import com.dxm.dxmcharge.base.BaseActivity
import com.dxm.dxmcharge.databinding.ActivityWelcomeBinding

class WelcomeActivity :BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, WelcomeActivity::class.java))
        }
    }


    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storageService().put(App.IS_APP_FIRST,true)

        binding.btWelcome.setOnClickListener{

        }

    }


}