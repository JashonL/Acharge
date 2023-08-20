package com.dxm.dxmcharge.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetWork {

    private val acountService = ServiceCreator.create<API.AccountService>()

    suspend fun register(
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
    ) = acountService.register(
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
    ).await()




    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if(body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }


}


