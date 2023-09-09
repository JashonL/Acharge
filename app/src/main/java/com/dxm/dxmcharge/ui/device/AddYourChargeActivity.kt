package com.dxm.dxmcharge.ui.device

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.charge.lib.util.ActivityBridge
import com.charge.lib.util.LanUtils
import com.charge.lib.util.ToastUtil
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.base.BaseActivity
import com.dxm.dxmcharge.databinding.AddYourChargeBinding
import com.dxm.dxmcharge.ui.device.monitor.ChargeAactivityMonitor
import com.dxm.dxmcharge.ui.common.activity.ScanActivity
import com.dxm.dxmcharge.ui.common.fragment.RequestPermissionHub


class AddYourChargeActivity : BaseActivity(), View.OnClickListener {


    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, AddYourChargeActivity::class.java))
        }
    }


    private lateinit var binding: AddYourChargeBinding

    private val addChargeViewModel by lazy {
        ViewModelProvider(this)[AddChargeViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddYourChargeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initViews()
        initListeners()
    }

    private fun initListeners() {
        binding.btAdd.setOnClickListener(this)
        binding.llScan.setOnClickListener(this)

    }

    private fun initViews() {


    }


    private fun initData() {

        addChargeViewModel.newLiveData.observe(this){
            dismissDialog()
            val provinceList = it.getOrNull()
            if (!provinceList.isNullOrEmpty()) {//添加成功
                ChargeAactivityMonitor.notifyPlant()
                finish()
            } else {
                val message = it.exceptionOrNull()?.message
                ToastUtil.show(message)

            }
        }




    }

    override fun onClick(p0: View?) {
        when {
            p0 === binding.btAdd -> {
                val text = binding.etSn.text.toString().trim()
                val pin = binding.etPin.text.toString().trim()

                when {
                    text.isEmpty() -> {
                        ToastUtil.show(getString(R.string.m116_sn_not_null))
                    }
                /*    pin.isEmpty() -> {
                        ToastUtil.show(getString(R.string.m185_pin_not_empty))
                    }*/
                    else -> {
                        showDialog()


                        val language = LanUtils.getLanguage(this)
                        showDialog()
                        addChargeViewModel.addChage("list",accountService().user()?.userId,text,language.toString())
                    }
                }
            }

            p0 === binding.llScan -> {
                RequestPermissionHub.requestPermission(
                    supportFragmentManager,
                    arrayOf(Manifest.permission.CAMERA)
                ) {
                    if (it) {
                        scan()
                    }
                }
            }


        }
    }


    private fun scan() {
        ActivityBridge.startActivity(
            this,
            ScanActivity.getIntent(this),
            object : ActivityBridge.OnActivityForResult {
                override fun onActivityForResult(
                    context: Context?,
                    resultCode: Int,
                    data: Intent?
                ) {
                    if (resultCode == RESULT_OK && data?.hasExtra(ScanActivity.KEY_CODE_TEXT) == true) {
                        val collectionSN = data.getStringExtra(ScanActivity.KEY_CODE_TEXT)
                        binding.etSn.setText(collectionSN)
                        /*  showDialog()
                          addChargeViewModel.addChage(collectionSN)*/
                    }
                }
            })
    }

}