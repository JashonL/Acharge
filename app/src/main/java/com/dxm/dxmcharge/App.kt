package com.dxm.dxmcharge

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.charge.lib.storage.ServiceType
import com.charge.lib.storage.service.DefaultStorageService
import com.charge.lib.storage.service.IStorageService
import com.charge.lib.storage.service.ServiceManager

class App : Application(), ServiceManager.ServiceInterface {

    companion object {

        const val IS_APP_FIRST = "IS_APP_FIRST"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        @SuppressLint("StaticFieldLeak")
        private lateinit var instance: App
        fun instance() = instance

    }

    override fun onCreate() {
        super.onCreate()
        context = this
        instance=this
        registerService()

    }


    private fun registerService() {
        ServiceManager.instance()
            .registerService(ServiceType.STORAGE, DefaultStorageService(this))
    }

    override fun storageService(): IStorageService {
        return ServiceManager.instance().getService(ServiceType.ACCOUNT) as DefaultStorageService
    }


    /**
     * 是否同意隐私政策
     */
    fun isIsFirst(): Boolean {
        return storageService().getBoolean(IS_APP_FIRST, false)
    }


}