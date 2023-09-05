package com.dxm.dxmcharge.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.charge.lib.storage.ServiceType
import com.charge.lib.storage.account.IAccountService
import com.charge.lib.storage.service.DefaultStorageService
import com.charge.lib.storage.service.IStorageService
import com.charge.lib.storage.service.ServiceManager
import com.dxm.dxmcharge.App

abstract class BaseActivity : AppCompatActivity(), ServiceManager.ServiceInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun storageService(): IStorageService {
        return App.instance().storageService()
    }


    override fun accountService(): IAccountService {
        return App.instance().accountService()
    }


}