package com.dxm.dxmcharge.ui.account.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dxm.dxmcharge.R

class LoginActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, LoginActivity::class.java))
        }

        fun startClearTask(context: Context?) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context?.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}