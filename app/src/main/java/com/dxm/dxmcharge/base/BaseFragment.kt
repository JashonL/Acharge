package com.dxm.dxmcharge.base

import android.view.View
import androidx.fragment.app.Fragment
import com.charge.lib.storage.account.IAccountService
import com.charge.lib.storage.service.IDeviceService
import com.charge.lib.storage.service.IStorageService
import com.charge.lib.storage.service.ServiceManager
import com.dxm.dxmcharge.App
import com.tianji.ttech.base.IDisplay


abstract class BaseFragment : Fragment(), ServiceManager.ServiceInterface, IDisplay {



    override fun storageService(): IStorageService {
        return App.instance().storageService()
    }

    override fun deviceService(): IDeviceService {
        return App.instance().deviceService()
    }

    override fun accountService(): IAccountService {
        return App.instance().accountService()
    }



    override fun showDialog() {
        (activity as? BaseActivity)?.showDialog()
    }

    override fun dismissDialog() {
        (activity as? BaseActivity)?.dismissDialog()
    }

    override fun showPageErrorView(onRetry: ((view: View) -> Unit)) {
        (activity as? BaseActivity)?.showPageErrorView(onRetry)
    }

    override fun hidePageErrorView() {
        (activity as? BaseActivity)?.hidePageErrorView()
    }

    override fun showPageLoadingView() {
        (activity as? BaseActivity)?.showPageLoadingView()
    }

    override fun hidePageLoadingView() {
        (activity as? BaseActivity)?.hidePageLoadingView()
    }

    override fun showResultDialog(
        result: String?,
        onCancelClick: (() -> Unit)?,
        onComfirClick: (() -> Unit)?
    ) {
        (activity as? BaseActivity)?.showResultDialog(result, onCancelClick, onComfirClick)
    }

}