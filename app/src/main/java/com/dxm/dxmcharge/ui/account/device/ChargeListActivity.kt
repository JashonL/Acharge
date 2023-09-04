package com.dxm.dxmcharge.ui.account.device

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dxm.dxmcharge.R

class ChargeListActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, ChargeListActivity::class.java))
        }

        fun startClearTask(context: Context?) {
            val intent = Intent(context, ChargeListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context?.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charge_list)
    }
}