package com.charge.lib.storage.service

import com.charge.lib.storage.Service
import com.charge.lib.storage.ServiceType
import com.charge.lib.storage.account.IAccountService


/**
 * 服务管理类
 * 隔离层:网络加载库、简单的key-value存储、设备信息、账号信息
 */
class ServiceManager private constructor() {

    private val serviceMap = hashMapOf<String, Service>()

    //静态内部类实现单例模式
    companion object {
        fun instance(): ServiceManager = Holder.instance
    }

    private object Holder {
        val instance = ServiceManager()
    }


    fun getService(@ServiceType type: String): Service? {
        return serviceMap[type]
    }


    fun registerService(@ServiceType type: String, service: Service) {
        serviceMap[type] = service
    }
    interface ServiceInterface {
        fun storageService(): IStorageService
        fun accountService(): IAccountService
        fun deviceService(): IDeviceService
    }
}