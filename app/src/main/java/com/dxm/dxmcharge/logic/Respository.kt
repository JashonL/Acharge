package com.dxm.dxmcharge.logic

import androidx.lifecycle.liveData
import com.dxm.dxmcharge.base.BaseException
import com.dxm.dxmcharge.logic.network.SunnyWeatherNetWork
import com.google.gson.Gson
import com.shuoxd.lib.service.account.User
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
//               Result.failure(RuntimeException("repsponse status is ${register.code}"))


               Result.failure(RuntimeException(register.data))

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



    fun login(body: RequestBody) = liveData(Dispatchers.IO) {
        val result = try {
            val login = SunnyWeatherNetWork.login(body)
            //Result这个类kotlin是内置的
            if (login.code=="0"){
                Result.success(login.data)
            } else {
//               Result.failure(RuntimeException("repsponse status is ${register.code}"))
                Result.failure(RuntimeException("repsponse status is "))
            }
        } catch (e: Exception) {
            if (e is BaseException){
                Result.failure(RuntimeException(e.errorMsg))
            }else{
                Result.failure(e)
            }

        }
        emit(result)

    }




    fun getChargeList(body: RequestBody) = liveData(Dispatchers.IO) {
        val result = try {
            val chargelist = SunnyWeatherNetWork.getchargelist(body)
            //Result这个类kotlin是内置的
            if (login.code=="0"){
                Result.success(chargelist.data)
            } else {
//               Result.failure(RuntimeException("repsponse status is ${register.code}"))
                Result.failure(RuntimeException("repsponse status is "))
            }
        } catch (e: Exception) {
            if (e is BaseException){
                Result.failure(RuntimeException(e.errorMsg))
            }else{
                Result.failure(e)
            }

        }
        emit(result)

    }





}