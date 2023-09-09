package com.dxm.dxmcharge.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.charge.lib.storage.account.IAccountService
import com.charge.lib.storage.service.IDeviceService
import com.charge.lib.storage.service.IStorageService
import com.charge.lib.storage.service.ServiceManager
import com.dxm.dxmcharge.App
import com.dxm.dxmcharge.logic.model.Charge
import com.dxm.dxmcharge.service.charge.ChargeManager

abstract class BaseActivity : AppCompatActivity(), ServiceManager.ServiceInterface, IDisplay ,
    ChargeManager.ServiceInterface {


    private val display: IDisplay by lazy(LazyThreadSafetyMode.NONE) {
        AndroidDisplay(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun deviceService(): IDeviceService {
        return App.instance().deviceService()
    }

    override fun storageService(): IStorageService {
        return App.instance().storageService()
    }


    override fun accountService(): IAccountService {
        return App.instance().accountService()
    }




    override fun setChargeList(list: MutableList<Charge>) {
        ChargeManager.getInstance().setChargeList(list)
    }


    override fun getChargeList(): MutableList<Charge> {
        return ChargeManager.getInstance().getChargeList()
    }


    override fun setCurrenChargeModel(chargeModel: Charge) {
        ChargeManager.getInstance().setCurrentCharge(chargeModel)

    }

    override fun getCurrentChargeModel(): Charge? {

        return ChargeManager.getInstance().getCurrentCharge()
    }


    override fun showDialog() {
        display.showDialog()
    }

    override fun dismissDialog() {
        display.dismissDialog()
    }

    override fun showPageErrorView(onRetry: ((view: View) -> Unit)) {
        display.showPageErrorView(onRetry)
    }

    override fun hidePageErrorView() {
        display.hidePageErrorView()
    }

    override fun showPageLoadingView() {
        display.showPageLoadingView()
    }

    override fun hidePageLoadingView() {
        display.hidePageLoadingView()
    }






    override fun showResultDialog(
        result: String?,
        onCancelClick: (() -> Unit)?,
        onComfirClick: (() -> Unit)?
    ) {
        display.showResultDialog(result, onCancelClick, onComfirClick)
    }

}