package com.dxm.dxmcharge.base

import androidx.lifecycle.ViewModel
import com.charge.lib.storage.account.IAccountService
import com.charge.lib.storage.service.IDeviceService
import com.charge.lib.storage.service.IStorageService
import com.charge.lib.storage.service.ServiceManager
import com.dxm.dxmcharge.App

open class BaseViewModel : ViewModel(), ServiceManager.ServiceInterface {


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