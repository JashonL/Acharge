package com.dxm.dxmcharge.ui.gun

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.charge.lib.util.LanUtils
import com.charge.lib.util.ToastUtil
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.base.BaseFragment
import com.dxm.dxmcharge.databinding.FragmentGunBinding
import com.dxm.dxmcharge.extend.gone
import com.dxm.dxmcharge.extend.visible
import com.dxm.dxmcharge.service.charge.ChargeStatus
import com.dxm.dxmcharge.ui.settting.DelayedChargingActivity
import com.dxm.dxmcharge.util.DateUtils
import com.dxm.dxmcharge.util.ValueUtil
import com.dxm.dxmcharge.widget.OnTabSelectedListener
import com.dxm.dxmcharge.widget.Tab
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"

class GunFragment : BaseFragment() {


    private var param1: String? = null


    val ChargingModel by lazy { ViewModelProvider(this)[ChargingModel::class.java] }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            GunFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }


    private lateinit var bind: FragmentGunBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentGunBinding.inflate(inflater)
        initviews()
        getInfo()
        initCharge()
        initLiseners()
        timerStart()
        return bind.root
    }

    private fun initCharge() {
        ChargingModel.actionLiveData.observe(viewLifecycleOwner) {
            dismissDialog()
            if (it.getOrNull() != null) {
                ToastUtil.show(it.getOrNull())
            } else {
                val message = it.exceptionOrNull()?.message
                ToastUtil.show(message)
            }

            //成功或者失败都立刻刷新一次(延迟3秒)
            lifecycleScope.launch {
                delay(3000)
                getChargeInfo()
            }
        }
    }


    //启动定时任务
    private fun timerStart() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                repeat(Int.MAX_VALUE) {
                    getChargeInfo()
                    delay(15 * 1000)
                }
            }
        }

    }


    private fun initviews() {
        bind.tabLayout.addTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelect(selectTab: Tab, selectPosition: Int) {
                when (selectPosition) {
                    0 -> {
                        bind.chageinfo.llCharge.visible()
                        bind.batinfo.llBat.gone()
                        bind.other.clOther.gone()
                    }
                    1 -> {
                        bind.chageinfo.llCharge.gone()
                        bind.batinfo.llBat.visible()
                        bind.other.clOther.gone()
                    }
                    2 -> {
                        bind.chageinfo.llCharge.gone()
                        bind.batinfo.llBat.gone()
                        bind.other.clOther.visible()
                    }
                }

            }

        })
        bind.tabLayout.setSelectTabPosition(0, true)

    }

    private fun initLiseners() {
        bind.button.btLogin.setOnClickListener {
            chargeAction()
        }

        bind.other.llFast.setOnClickListener {
//            ToastUtil.show(getString(R.string.wait_dev))
        }

        bind.other.llLocked.setOnClickListener {
            ToastUtil.show(getString(R.string.wait_dev))
        }

        bind.other.llAppointmentCharge.setOnClickListener {
            ToastUtil.show(getString(R.string.wait_dev))
        }
        bind.other.llDelayCharging.setOnClickListener {
            val i = bind.tabLayout.getSelectTabPosition() + 1
            DelayedChargingActivity.start(context,i.toString())
        }


    }


    private fun getInfo() {
        //充电桩详情
        ChargingModel.newLiveData.observe(viewLifecycleOwner) { it ->
            dismissDialog()

            it.getOrNull()?.let {
                val valueFromA = ValueUtil.valueFromA(it.current)
                val valueFromV = ValueUtil.valueFromV(it.voltage)
                //电压电流
                bind.power.tvCurrent.text = valueFromA.first + valueFromA.second
                bind.power.tvVoltage.text = valueFromV.first + valueFromV.second
                //状态
                val chargeStatus = ChargeStatus.getChargeStatus(it.status)
                ChargingModel.status = chargeStatus


                val model = getCurrentChargeModel()?.model
                bind.charge.tvAc.text = "$model | $chargeStatus"

                if (ChargeStatus.CHARGING.equals(chargeStatus)) {
                    bind.charge.chargeView.setValueAndStartAnim()
                    bind.button.btLogin.text = getString(R.string.m182_stop)
                } else {
                    bind.charge.chargeView.stopAnim()
                    bind.button.btLogin.text = getString(R.string.m181_start)
                }

                //充电信息
                val symbol: String? = it.symbol
                //已充金额
                val cost: Double? = it.cost
                bind.chageinfo.divChargeCost.setSubName(symbol + cost)


                //已充电量
                val energy: Double? = it.energy
                bind.chageinfo.divChargeEle.setSubName(energy.toString() + "kWh")

                //已充时长
                val timeCharging = it.ctime
                val hourCharging = timeCharging?.div(60)
                val minCharging = timeCharging?.rem(60)
                val sTimeCharging = hourCharging.toString() + "h" + minCharging + "min"
                bind.chageinfo.divChargeTime.setSubName(sTimeCharging)
                //费率
                val rate = "$symbol${it.rate}/kWh"
                bind.chageinfo.divChargeRate.setSubName(rate)


                //开始时间
                if (it.sysStartTime != null) {
                    val yyyyMmDdHhMmSsFormat = DateUtils.yyyy_MM_dd_hh_mm_ss_format(it.sysStartTime)
                    bind.batinfo.divStartTime.setSubName(yyyyMmDdHhMmSsFormat)
                }

                //结束时间
                if (it.sysEndTime != null) {
                    val yyyyMmDdHhMmSsFormat = DateUtils.yyyy_MM_dd_hh_mm_ss_format(it.sysEndTime)
                    bind.batinfo.divEndTime.setSubName(yyyyMmDdHhMmSsFormat)
                }


                //预设的值
                val presetValue: Double? = it.cValue



                when (it.ckey) {
                    "G_SetAmount" -> {
                        bind.chageinfo.divType.setSubName(getString(R.string.m120_money))
                        //计算占比
                        //当前已充金额
                        //当前已充金额
                        if (cost!! < presetValue!!) {
                            val v = cost * 100 / presetValue
                            bind.charge.tvPercent.text = "$v%"
                        }
                    }
                    "G_SetEnergy" -> {
                        bind.chageinfo.divType.setSubName(getString(R.string.m118_electricity))
                        if (energy!! < presetValue!!) {
                            val v = energy * 100 / presetValue
                            bind.charge.tvPercent.text = "$v%"
                        }

                    }
                    "G_SetTime" -> {
                        bind.chageinfo.divType.setSubName(getString(R.string.m119_time))
                        if (timeCharging!! < presetValue!!) {
                            val v = timeCharging / presetValue * 100
                            bind.charge.tvPercent.text = "$v%"
                        }
                    }
                    else -> {
                        bind.chageinfo.divType.setSubName("")
                        bind.charge.tvPercent.text = "0%"
                    }
                }


            }


        }


        showDialog()
        getChargeInfo()


    }


    private fun getChargeInfo() {
        val chargeId = getCurrentChargeModel()?.chargeId
        val language = LanUtils.getLanguage(requireContext())
        ChargingModel.getChargeInfo(
            "info",
            accountService().user()?.userId,
            chargeId,
            param1,
            language.toString()
        )
    }


    private fun chargeAction() {
        if (ChargeStatus.CHARGING.equals(ChargingModel.status)) {
            startCharge("remoteStopTransaction")
        } else {
            startCharge("remoteStartTransaction")

        }

    }


    private fun startCharge(action: String) {
        val chargeId = getCurrentChargeModel()?.chargeId
        val language = LanUtils.getLanguage(requireContext())
        ChargingModel.charge(
            action,
            accountService().user()?.userId,
            chargeId,
            param1,
            language.toString()
        )
    }


}