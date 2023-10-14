package com.dxm.dxmcharge.ui.charge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.charge.lib.util.DateUtils
import com.charge.lib.util.LanUtils
import com.charge.lib.util.ToastUtil
import com.charge.lib.view.dialog.DatePickerFragment
import com.charge.lib.view.dialog.OnDateSetListener
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.base.BaseActivity
import com.dxm.dxmcharge.databinding.ActivityChargePresetBinding
import com.dxm.dxmcharge.extend.gone
import com.dxm.dxmcharge.extend.visible
import com.dxm.dxmcharge.logic.model.ReserveNow
import java.util.*

class ChargePresetActivity : BaseActivity(), OnClickListener {


    companion object {
        fun start(context: Context?, connectorId: String) {
            context?.startActivity(Intent(context, ChargePresetActivity::class.java).apply {
                putExtra("connectorId", connectorId)
            })
        }
    }


    //预约类型1时长  2金额 3电量
    private var cKey = "G_SetEnergy"
    private var connectorId: String? = ""


    private val chargePresetViewModel by lazy { ViewModelProvider(this)[ChargePresetViewModel::class.java] }

    private lateinit var binding: ActivityChargePresetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargePresetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initLiseners()
        initData()
    }

    private fun initLiseners() {
        binding.ele.llTimeOn.setOnClickListener(this)
        binding.money.llTimeOn.setOnClickListener(this)
        binding.duration.llTimeOn.setOnClickListener(this)
        binding.none.llTimeOn.setOnClickListener(this)
        binding.btLogin.setOnClickListener(this)


        binding.cardCost.setOnClickListener(this)
        binding.cardEle.setOnClickListener(this)
        binding.cardDuration.setOnClickListener(this)

    }

    private fun initData() {

        chargePresetViewModel.setReserveNowInfoLiveData.observe(this) {
            dismissDialog()
            val orNull = it.getOrNull()
            if (orNull != null) {
                ToastUtil.show(orNull)
                finish()
            } else {
                val message = it.exceptionOrNull()?.message
                ToastUtil.show(message)
            }

        }



        chargePresetViewModel.newLiveData.observe(this) {
            dismissDialog()
            val orNull = it.getOrNull()
            if (orNull != null && !orNull.isEmpty()) {
                val get = orNull[0]
                cKey = get.cKey
                showViewsByPreset()
                //预约电量
                val cValue = get.cValue
                //定额
                val cValue2 = get.cValue2
                //开始时间
                val expiryDate = get.expiryDate

                binding.money.etCost.setText(cValue2)
                if (cKey == "G_SetEnergy") {
                    binding.ele.etCvalue.setText(cValue)
                    binding.ele.etStartTime.setText(expiryDate)

                } else if (cKey == "G_SetTime") {
                    binding.duration.etCvalue.setText(cValue)
                    binding.duration.etStartTime.setText(expiryDate)
                } else if (cKey == "G_SetAmount") {
                    binding.money.etStartTime.setText(expiryDate)
                } else {
                    binding.none.etStartTime.setText(expiryDate)
                }


            } else {
                val message = it.exceptionOrNull()?.message
                ToastUtil.show(message)
            }


        }


        connectorId = intent.getStringExtra("connectorId")

        val chargeId = getCurrentChargeModel()?.chargeId
        val language = LanUtils.getLanguage(this)
        chargePresetViewModel.reserveNow(
            "ReserveNow",
            accountService().user()?.userId,
            chargeId,
            connectorId,
            language.toString()
        )


    }


    private fun initViews() {
        showViewsByPreset()

    }


    override fun onClick(p0: View?) {
        when {
            p0 === binding.cardCost -> {
                cKey = if (cKey == "G_SetAmount") {
                    ""
                } else {
                    "G_SetAmount"
                }
                setChoose()
            }

            p0 === binding.cardDuration -> {
                cKey = if (cKey == "G_SetTime") {
                    ""
                } else {
                    "G_SetTime"
                }
                setChoose()
            }
            p0 === binding.cardEle -> {
                cKey = if (cKey == "G_SetEnergy") {
                    ""
                } else {
                    "G_SetEnergy"
                }
                setChoose()
            }

            p0 === binding.ele.llTimeOn -> {
                selectDate(binding.ele.etStartTime)
            }

            p0 === binding.money.llTimeOn -> {
                selectDate(binding.money.etStartTime)

            }
            p0 === binding.duration.llTimeOn -> {
                selectDate(binding.duration.etStartTime)
            }
            p0 === binding.none.llTimeOn -> {
                selectDate(binding.none.etStartTime)
            }
            p0 === binding.btLogin -> {
                remoteStartTransaction()
            }
        }
    }


    public fun remoteStartTransaction() {
        when (cKey) {
            "G_SetAmount" -> {
                //预约时间
                val startTime = binding.money.etStartTime.text
                val cValue = binding.money.etCost.text ?: ""
                if (TextUtils.isEmpty(startTime)) {
                    ToastUtil.show(getString(R.string.enter_start_time))
                    return
                }
                if (TextUtils.isEmpty(cValue)) {
                    ToastUtil.show(getString(R.string.enter_preset_value))
                    return
                }

                val chargeId = getCurrentChargeModel()?.chargeId
                val language = LanUtils.getLanguage(this)

                showDialog()
                chargePresetViewModel.setReserveNow(
                    "ReserveNow",
                    cKey,
                    cValue.toString(),
                    chargeId,
                    connectorId,
                    accountService().user()?.userId,
                    language.toString()
                )


            }
            "G_SetEnergy" -> {

                //预约时间
                val startTime = binding.ele.etStartTime.text
                val cValue = binding.ele.etCvalue.text ?: ""
                if (TextUtils.isEmpty(startTime)) {
                    ToastUtil.show(getString(R.string.enter_start_time))
                    return
                }
                if (TextUtils.isEmpty(cValue)) {
                    ToastUtil.show(getString(R.string.enter_preset_value))
                    return
                }

                val chargeId = getCurrentChargeModel()?.chargeId
                val language = LanUtils.getLanguage(this)

                showDialog()
                chargePresetViewModel.setReserveNow(
                    "ReserveNow",
                    cKey,
                    cValue.toString(),
                    chargeId,
                    connectorId,
                    accountService().user()?.userId,
                    language.toString()
                )


            }
            "G_SetTime" -> {
                //预约时间
                val startTime = binding.duration.etStartTime.text
                val cValue = binding.duration.etCvalue.text ?: ""
                if (TextUtils.isEmpty(startTime)) {
                    ToastUtil.show(getString(R.string.enter_start_time))
                    return
                }
                if (TextUtils.isEmpty(cValue)) {
                    ToastUtil.show(getString(R.string.enter_preset_value))
                    return
                }

                val chargeId = getCurrentChargeModel()?.chargeId
                val language = LanUtils.getLanguage(this)

                showDialog()
                chargePresetViewModel.setReserveNow(
                    "ReserveNow",
                    cKey,
                    cValue.toString(),
                    chargeId,
                    connectorId,
                    accountService().user()?.userId,
                    language.toString()
                )
            }
            else -> {
                //只预约开始时间

                //预约时间
                val startTime = binding.none.etStartTime.text
                if (TextUtils.isEmpty(startTime)) {
                    ToastUtil.show(getString(R.string.enter_start_time))
                    return
                }


                val chargeId = getCurrentChargeModel()?.chargeId
                val language = LanUtils.getLanguage(this)

                showDialog()
                chargePresetViewModel.setReserveNow(
                    "ReserveNow",
                    cKey,
                    "",
                    chargeId,
                    connectorId,
                    accountService().user()?.userId,
                    language.toString()
                )
            }
        }


    }


    public fun setChoose() {
        when (cKey) {
            "G_SetAmount" -> {
                binding.cardCost.setCardBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_28BEF1
                    )
                )
                binding.cardDuration.setCardBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
                binding.cardEle.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))




                binding.tvAmountOfMoney.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )

                binding.tvDuration.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_text_66
                    )
                )
                binding.tvQuantityOfElectricity.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_text_66
                    )
                )


            }
            "G_SetEnergy" -> {
                binding.cardCost.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.cardDuration.setCardBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
                binding.cardEle.setCardBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_28BEF1
                    )
                )


                binding.tvAmountOfMoney.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_text_66
                    )
                )

                binding.tvDuration.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_text_66
                    )
                )
                binding.tvQuantityOfElectricity.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )


            }
            "G_SetTime" -> {
                binding.cardCost.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.cardDuration.setCardBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_28BEF1
                    )
                )
                binding.cardEle.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))

                binding.tvAmountOfMoney.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_text_66
                    )
                )

                binding.tvDuration.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
                binding.tvQuantityOfElectricity.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_text_66
                    )
                )

            }
            else -> {
                binding.cardCost.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.cardDuration.setCardBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
                binding.cardEle.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))



                binding.tvAmountOfMoney.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_text_66
                    )
                )

                binding.tvDuration.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_text_66
                    )
                )
                binding.tvQuantityOfElectricity.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_text_66
                    )
                )
            }
        }


    }


    private fun selectDate(etView: TextView) {
        DatePickerFragment.show(supportFragmentManager, object : OnDateSetListener {
            override fun onDateSet(date: Date) {
                val yyyyMmDdHhMmSsSssFormat = DateUtils.yyyy_MM_dd_HH_mm_ss_SSS_format(date)
                etView.setText(yyyyMmDdHhMmSsSssFormat)
            }
        })
    }


    private fun showViewsByPreset() {
        when (cKey) {
            "G_SetAmount" -> {
                binding.duration.llDuration.visible()
                binding.money.llMoney.gone()
                binding.ele.llEle.gone()
                binding.none.llNone.gone()
            }
            "G_SetEnergy" -> {
                binding.duration.llDuration.gone()
                binding.money.llMoney.visible()
                binding.ele.llEle.gone()
                binding.none.llNone.gone()
            }
            "G_SetTime" -> {
                binding.duration.llDuration.gone()
                binding.money.llMoney.gone()
                binding.ele.llEle.visible()
                binding.none.llNone.gone()
            }
            else -> {
                binding.duration.llDuration.gone()
                binding.money.llMoney.gone()
                binding.ele.llEle.gone()
                binding.none.llNone.visible()
            }
        }

        setChoose()


    }


}