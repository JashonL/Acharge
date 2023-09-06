package com.tianji.ttech.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.charge.lib.storage.account.IAccountService
import com.charge.lib.storage.service.IStorageService
import com.charge.lib.storage.service.ServiceManager
import com.dxm.dxmcharge.App
import com.dxm.dxmcharge.base.BaseActivity
import com.charge.lib.storage.service.IDeviceService
import com.shuoxd.lib.util.ViewUtil

/**
 * 基础DialogFragment
 */
open class BaseDialogFragment : DialogFragment(), ServiceManager.ServiceInterface {

    override fun onStart() {
        super.onStart()
        //设置左右边距
        requireDialog().window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = App.instance().deviceService().getDeviceWidth() - ViewUtil.dp2px(
            requireContext(),
            35f * 2
        )
        requireDialog().window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
    }



    override fun storageService(): IStorageService {
        return App.instance().storageService()
    }

    override fun deviceService(): IDeviceService {
        return App.instance().deviceService()
    }

    override fun accountService(): IAccountService {
        return App.instance().accountService()
    }


}