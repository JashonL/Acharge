package com.dxm.dxmcharge.ui.charge

import android.Manifest
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.charge.lib.util.LanUtils
import com.charge.lib.util.ToastUtil
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.base.BaseActivity
import com.dxm.dxmcharge.databinding.ActivityChargePresetBinding
import com.dxm.dxmcharge.databinding.AddYourChargeBinding
import com.dxm.dxmcharge.ui.common.fragment.RequestPermissionHub
import com.dxm.dxmcharge.ui.device.AddYourChargeActivity

class ChargePresetActivity : BaseActivity() ,OnClickListener{


    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, ChargePresetActivity::class.java))
        }
    }


    //预约类型1时长  2金额 3电量
    private var preset = 1;


    private lateinit var binding: ActivityChargePresetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChargePresetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        showViewsByPreset()
    }

    private fun initViews() {


    }





    override fun onClick(p0: View?) {

    }


    private fun showViewsByPreset() {
        when (preset) {
            1 -> {}
            2 -> {}
            3 -> {}
        }


    }


}