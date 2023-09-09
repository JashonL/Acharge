package com.dxm.dxmcharge.ui.common.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import com.charge.scan.CaptureHelper
import com.charge.scan.OnCaptureCallback
import com.dxm.dxmcharge.base.BaseActivity
import com.dxm.dxmcharge.databinding.ActivityScanBinding

/**
 * 扫码Activity
 */
class ScanActivity : BaseActivity(), OnCaptureCallback {

    companion object {

        const val KEY_CODE_TEXT = "key_code_text"

        fun start(context: Context?) {
            context?.startActivity(getIntent(context))
        }

        fun getIntent(context: Context?): Intent {
            return Intent(context, ScanActivity::class.java)
        }

    }

    private lateinit var binding: ActivityScanBinding
    private val helper by lazy {
        CaptureHelper(this, binding.surface, binding.viewfinderView, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        helper.also {
            it.setOnCaptureCallback(this)
            it.onCreate()
            it.vibrate(true)
            it.framingRectRatio(0.625f)
            it.supportVerticalCode(true)
        }
    }

    override fun onResultCallback(result: String?): Boolean {
        result?.also {
            val intent = Intent()
            intent.putExtra(KEY_CODE_TEXT, result)
            setResult(RESULT_OK, intent)
            finish()
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        helper.onResume()
    }

    override fun onPause() {
        super.onPause()
        helper.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        helper.onDestroy()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        helper.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

}