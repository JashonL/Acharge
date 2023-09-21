package com.dxm.dxmcharge.logic

import androidx.lifecycle.liveData
import com.dxm.dxmcharge.base.BaseException
import com.dxm.dxmcharge.logic.network.SunnyWeatherNetWork
import com.google.gson.Gson
import com.shuoxd.lib.service.account.User
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

object Respository {

    fun register(body: RequestBody) = liveData(Dispatchers.IO) {
        val result = try {
            val register = SunnyWeatherNetWork.register(body)
            //Result这个类kotlin是内置的
            if (register.code == "0") {
                Result.success(register.data)
            } else {
//               Result.failure(RuntimeException("repsponse status is ${register.code}"))


                Result.failure(RuntimeException(register.data))

            }
        } catch (e: Exception) {
            if (e is BaseException) {
                Result.failure(RuntimeException(e.errorMsg))
            } else {
                Result.failure(e)
            }
        }
        emit(result)

    }


    fun countrylist(body: RequestBody) = liveData(Dispatchers.IO) {
        val result = try {
            val register = SunnyWeatherNetWork.getcountry(body)
            //Result这个类kotlin是内置的
            if (register.code == "0") {
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
            if (login.code == "0") {
                Result.success(login.data)
            } else {
//               Result.failure(RuntimeException("repsponse status is ${register.code}"))
                Result.failure(RuntimeException("repsponse status is "))
            }
        } catch (e: Exception) {
            if (e is BaseException) {
                Result.failure(RuntimeException(e.errorMsg))
            } else {
                Result.failure(e)
            }

        }
        emit(result)

    }


    fun getChargeList(body: RequestBody) = liveData(Dispatchers.IO) {
        val result = try {
            val chargelist = SunnyWeatherNetWork.getchargelist(body)
            //Result这个类kotlin是内置的
            if (chargelist.code == "0") {
                Result.success(chargelist.data)
            } else {
//               Result.failure(RuntimeException("repsponse status is ${register.code}"))
                Result.failure(RuntimeException("repsponse status is "))
            }
        } catch (e: Exception) {
            if (e is BaseException) {
                Result.failure(RuntimeException(e.errorMsg))
            } else {
                Result.failure(e)
            }

        }
        emit(result)

    }


    fun addCharge(body: RequestBody) = liveData(Dispatchers.IO) {
        val result = try {
            val add = SunnyWeatherNetWork.addCharge(body)
            //Result这个类kotlin是内置的
            if (add.code == "0") {
                Result.success(add.data)
            } else {
                Result.failure(RuntimeException("repsponse status is "))
            }
        } catch (e: Exception) {
            if (e is BaseException) {
                Result.failure(RuntimeException(e.errorMsg))
            } else {
                Result.failure(e)
            }

        }
        emit(result)

    }


    fun getChargingData(body: RequestBody) = liveData(Dispatchers.IO) {
        val result = try {
            val add = SunnyWeatherNetWork.getChargingData(body)
            //Result这个类kotlin是内置的
            if (add.code == "0") {
                Result.success(add.data)
            } else {
                Result.failure(RuntimeException("repsponse status is "))
            }
        } catch (e: Exception) {
            if (e is BaseException) {
                Result.failure(RuntimeException(e.errorMsg))
            } else {
                Result.failure(e)
            }

        }
        emit(result)

    }


    fun action(body: RequestBody) = liveData(Dispatchers.IO) {
        val result = try {
            val add = SunnyWeatherNetWork.action(body)
            //Result这个类kotlin是内置的
            if (add.code == "0") {
                Result.success(add.data)
            } else {
                Result.failure(RuntimeException("repsponse status is "))
            }
        } catch (e: Exception) {
            if (e is BaseException) {
                Result.failure(RuntimeException(e.errorMsg))
            } else {
                Result.failure(e)
            }

        }
        emit(result)

    }


    fun delayStartTransaction(body: RequestBody) = liveData(Dispatchers.IO) {
        val result = try {
            val add = SunnyWeatherNetWork.delayStartTransaction(body)
            //Result这个类kotlin是内置的
            if (add.code == "0") {
                Result.success(add.data)
            } else {
                Result.failure(RuntimeException("repsponse status is "))
            }
        } catch (e: Exception) {
            if (e is BaseException) {
                Result.failure(RuntimeException(e.errorMsg))
            } else {
                Result.failure(e)
            }

        }
        emit(result)

    }


    fun reserveNow(body: RequestBody) = fire(Dispatchers.IO) {

        val add = SunnyWeatherNetWork.reserveNow(body)
        //Result这个类kotlin是内置的
        if (add.code == "0") {
            Result.success(add.data)
        } else {
            Result.failure(RuntimeException("repsponse status is "))
        }


/*        val result = try {
            val add = SunnyWeatherNetWork.reserveNow(body)
            //Result这个类kotlin是内置的
            if (add.code == "0") {
                Result.success(add.data)
            } else {
                Result.failure(RuntimeException("repsponse status is "))
            }
        } catch (e: Exception) {
            if (e is BaseException) {
                Result.failure(RuntimeException(e.errorMsg))
            } else {
                Result.failure(e)
            }

        }
        emit(result)*/

    }








    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                if (e is BaseException) {
                    Result.failure(RuntimeException(e.errorMsg))
                } else {
                    Result.failure<T>(e)
                }

            }
            emit(result)
        }


}