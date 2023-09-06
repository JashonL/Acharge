package com.dxm.dxmcharge.logic

import androidx.lifecycle.liveData
import com.dxm.dxmcharge.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody

object Respository {

    fun register(body: RequestBody) = liveData(Dispatchers.IO) {
        val result = try {
            val register = SunnyWeatherNetWork.register(body)
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




    fun countrylist(body: RequestBody) = liveData(Dispatchers.IO) {
        val result = try {
            val register = SunnyWeatherNetWork.getcountry(body)
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