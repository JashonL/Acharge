package com.dxm.dxmcharge

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.charge.lib.storage.ServiceType
import com.charge.lib.storage.service.DefaultStorageService
import com.charge.lib.storage.service.IStorageService
import com.charge.lib.storage.service.ServiceManager

class App : Application() ,ServiceManager.ServiceInterface{

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        registerService()

    }





    private fun registerService() {
        ServiceManager.instance()
            .registerService(ServiceType.STORAGE, DefaultStorageService(this))
    }

    override fun storageService(): IStorageService {
        return ServiceManager.instance().getService(ServiceType.ACCOUNT) as DefaultStorageService
    }


}