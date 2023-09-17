package com.dxm.dxmcharge.ui.settting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.charge.lib.util.LanUtils
import com.charge.lib.util.ToastUtil
import com.dxm.dxmcharge.ChargeActivity
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.base.BaseActivity
import com.dxm.dxmcharge.databinding.ActivityDelayedChargingBinding
import com.dxm.dxmcharge.databinding.ActivityMainBinding
import com.dxm.dxmcharge.dialog.InputDialog
import com.dxm.dxmcharge.service.charge.ChargeStatus
import com.dxm.dxmcharge.ui.gun.GunFragment
import com.dxm.dxmcharge.util.DateUtils
import com.dxm.dxmcharge.util.ValueUtil
import java.util.*

class DelayedChargingActivity : BaseActivity() {

    companion object {
        fun start(context: Context?, connectorId: String?) {
            context?.startActivity(Intent(context, DelayedChargingActivity::class.java).apply {
                putExtra("connectorId", connectorId)
            })
        }

        fun startClearTask(context: Context?) {
            val intent = Intent(context, DelayedChargingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context?.startActivity(intent)
        }
    }


    private lateinit var bind: ActivityDelayedChargingBinding

    private var connectorId = "1"


    private var delaytime = 600
    val model by lazy { ViewModelProvider(this)[DelayedChargeModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDelayedChargingBinding.inflate(layoutInflater)
        setContentView(bind.root)
        check(1)
        initliseners()
        initData()
    }

    private fun initData() {

        connectorId = intent.getStringExtra("connectorId").toString()

        model.newLiveData.observe(this) {
            dismissDialog()
            it.getOrNull()?.let {
                ToastUtil.show(it)
            }

            it.exceptionOrNull()?.let {
                ToastUtil.show(it.message)

            }


        }
    }

    private fun initliseners() {
        bind.cbSetting.setOnClickListener {
            bind.cbDefault
            InputDialog.showDialog(
                supportFragmentManager,
                delaytime.toString(),
                getString(R.string.m18_confirm),
                getString(R.string.m16_cancel),
                getString(R.string.input_delay_time)
            ) {
                val string = getString(R.string.start_charge_second, delaytime.toString())
                delaytime = string.toInt()
                bind.tvStartCharging.text = string
                check(2)
            }

        }



        bind.btOk.setOnClickListener {
            showDialog()
            val chargeId = getCurrentChargeModel()?.chargeId
            val language = LanUtils.getLanguage(this)
            val addDateTimes = DateUtils.addDateTimes(Date(), delaytime)
            val format = DateUtils.yyyy_MM_dd_HH_mm_ss_format_2.format(addDateTimes)


            val isDelay = if (bind.cbDefault.isChecked) {
                "1"
            } else {
                "0"
            }


            model.getDelaycharge(
                "delayStartTransaction",
                accountService().user()?.userId,
                chargeId,
                connectorId,
                format,
                isDelay,
                "1",
                language.toString()
            )


        }


    }


    private fun check(viewId: Int) {
        if (viewId == 1) {
            bind.cbDefault.isChecked = true
            bind.cbSetting.isChecked = false
        } else {
            bind.cbDefault.isChecked = false
            bind.cbSetting.isChecked = true
        }


    }


}