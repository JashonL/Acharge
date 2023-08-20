package com.dxm.dxmcharge.logic

import androidx.lifecycle.liveData
import com.dxm.dxmcharge.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers

object Respository {

    fun register(
        cmd: String?,
        userId: String?,
        phone: String?,
        password: String?,
        email: String?,
        installerId: String?,
        zipCode: String?,
        country: String?,
        city: String?,
        carMode: String?,
        car_1: String?,
        car_2: String?,
        lan: String?
    ) = liveData(Dispatchers.IO) {
        val result = try {
            val register = SunnyWeatherNetWork.register(
                cmd,
                userId,
                phone,
                password,
                email,
                installerId,
                zipCode,
                country,
                city,
                carMode,
                car_1,
                car_2,
                lan
            )

            //Result这个类kotlin是内置的
           if (register.code=="0"){
               Result.success(register.data)
           } else {
               Result.failure(RuntimeException("repsponse status is ${register.code}"))
           }


        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)

    }

}