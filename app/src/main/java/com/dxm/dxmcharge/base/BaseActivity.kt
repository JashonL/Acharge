package com.dxm.dxmcharge.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.charge.lib.storage.ServiceType
import com.charge.lib.storage.service.DefaultStorageService
import com.charge.lib.storage.service.IStorageService
import com.charge.lib.storage.service.ServiceManager

abstract class BaseActivity : AppCompatActivity(), ServiceManager.ServiceInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }




    override fun storageService(): IStorageService {
        return ServiceManager.instance().getService(ServiceType.STORAGE) as DefaultStorageService
    }






    /**
     * 体验账号权限
     * 体验账号是否可以打开，默认是不可以
     */
    open fun isGuestCanOpen(): Boolean {
        return false
    }
}