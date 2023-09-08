package com.dxm.dxmcharge.logic.network

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetWork {

    private val acountService = ServiceCreator.create<API.AccountService>()

    suspend fun register(body: RequestBody) = acountService.register(body).await()
    suspend fun login(body: RequestBody) = acountService.login(body).await()
    suspend fun getcountry(body: RequestBody) = acountService.countryList(body).await()
    suspend fun getchargelist(body: RequestBody) = acountService.chargeList(body).await()




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


