package com.dxm.dxmcharge.ui.common.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager
import com.charge.lib.util.ToastUtil
import com.charge.lib.util.Util
import com.dxm.dxmcharge.App
import com.dxm.dxmcharge.R

/**
 * 系统定位服务未开启提示Dialog
 */
class SystemLocationDisableTipDialog : AppCompatDialogFragment() {

    companion object {

        private const val REQUEST_CODE_OPEN_LOCATION_SWITCH = 7862

        fun show(fragmentManager: FragmentManager, callback: ((isOpen: Boolean) -> Unit)? = null) {
            val dialog = SystemLocationDisableTipDialog()
            dialog.callback = callback
            dialog.show(fragmentManager, SystemLocationDisableTipDialog::class.java.name)
        }

    }

    private var callback: ((isOpen: Boolean) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).setTitle(R.string.m36_tip)
            .setMessage(R.string.m37_need_to_open_location_service)
            .setPositiveButton(
                R.string.m38_setting
            ) { _, _ -> openLocationSettingActivity() }
            .setNegativeButton(android.R.string.cancel, null).create()
    }

    private fun openLocationSettingActivity() {
        try {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(intent, REQUEST_CODE_OPEN_LOCATION_SWITCH)
        } catch (e: Exception) {
            ToastUtil.show(getString(R.string.m39_please_to_setting_page_location_service))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_OPEN_LOCATION_SWITCH) {
            val isOpen = Util.isSystemLocationEnable(App.instance())
            callback?.invoke(isOpen)
            if (isOpen) {
                dismiss()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}