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
    suspend fun addCharge(body: RequestBody) = acountService.addCharge(body).await()
    suspend fun getChargingData(body: RequestBody) = acountService.charge(body).await()
    suspend fun action(body: RequestBody) = acountService.action(body).await()
    suspend fun delayStartTransaction(body: RequestBody) = acountService.delayStartTransaction(body).await()
    suspend fun lock(body: RequestBody) = acountService.delayStartTransaction(body).await()
    suspend fun reserveNow(body: RequestBody) = acountService.reserveNow(body).await()
    suspend fun remoteStartTransaction(body: RequestBody) = acountService.remoteStartTransaction(body).await()


    suspend fun setReserveNow(body: RequestBody) = acountService.setReserveNow(body).await()
    suspend fun chargeRecord(body: RequestBody) = acountService.chargeRecord(body).await()


    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }


}


