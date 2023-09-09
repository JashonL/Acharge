package com.dxm.dxmcharge

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.LocaleList
import com.charge.lib.storage.ServiceType
import com.charge.lib.storage.account.IAccountService
import com.charge.lib.storage.service.*
import com.charge.lib.util.ToastUtil
import com.dxm.dxmcharge.device.DefaultDeviceService
import com.shuoxd.charge.service.account.DefaultAccountService

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

        ToastUtil.init(this)


    }


    private fun registerService() {

        ServiceManager.instance()
            .registerService(ServiceType.STORAGE, DefaultStorageService(this))
        ServiceManager.instance().registerService(ServiceType.ACCOUNT, DefaultAccountService())
        ServiceManager.instance().registerService(ServiceType.DEVICE, DefaultDeviceService(this))
        ServiceManager.instance().registerService(ServiceType.ACCOUNT, DefaultAccountService())
    }



    override fun storageService(): IStorageService {
        return ServiceManager.instance().getService(ServiceType.STORAGE) as DefaultStorageService
    }


    override fun accountService(): IAccountService {
        return ServiceManager.instance().getService(ServiceType.ACCOUNT) as IAccountService
    }



    override fun deviceService(): IDeviceService {
        return ServiceManager.instance().getService(ServiceType.DEVICE) as IDeviceService
    }





    fun initLanguage(context: Context) {
        val appLanguage = deviceService().getAppLanguage()
        if (appLanguage == Language.FOLLOW_SYSTEM) {
            return
        }

        context.resources?.configuration?.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                LocaleList.setDefault(LocaleList(appLanguage.locale))
                setLocales(LocaleList(appLanguage.locale))
            } else {
                setLocale(appLanguage.locale)
            }
            context.resources.updateConfiguration(this, context.resources.displayMetrics)
        }
    }


    /**
     * 是否同意隐私政策
     */
    fun isIsFirst(): Boolean {
        return storageService().getBoolean(IS_APP_FIRST, false)
    }


}