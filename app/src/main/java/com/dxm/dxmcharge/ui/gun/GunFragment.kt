package com.dxm.dxmcharge.ui.gun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.charge.lib.util.LanUtils
import com.dxm.dxmcharge.base.BaseFragment
import com.dxm.dxmcharge.databinding.FragmentGunBinding
import com.dxm.dxmcharge.util.ValueUtil


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
        getInfo()
        return bind.root
    }


    private fun getInfo() {
        //充电桩详情
        ChargingModel.newLiveData.observe(viewLifecycleOwner) { it ->
            dismissDialog()

            it.getOrNull()?.let {

                val valueFromA = ValueUtil.valueFromA(it.current)
                val valueFromV = ValueUtil.valueFromA(it.voltage)

                //电压电流
                bind.power.tvVoltage.text = valueFromA.first
                bind.power.tvCurrent.text = valueFromV.first

            }


        }

        val chargeId = getCurrentChargeModel()?.chargeId
        val language = LanUtils.getLanguage(requireContext())
        showDialog()
        ChargingModel.getChargeInfo(
            "info",
            accountService().user()?.userId,
            chargeId,
            param1,
            language.toString()
        )


    }


}