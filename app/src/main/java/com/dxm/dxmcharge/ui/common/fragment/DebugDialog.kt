package com.dxm.dxmcharge.ui.common.fragment

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.charge.lib.util.ToastUtil
import com.dxm.dxmcharge.App
import com.dxm.dxmcharge.BuildConfig

/**
 * 调试页面
 * 1.切换环境
 */
class DebugDialog : DialogFragment() {

    companion object {
        fun showDialog(fragmentManager: FragmentManager) {
            DebugDialog().show(fragmentManager, DebugDialog::class.java.name)
        }
    }


}