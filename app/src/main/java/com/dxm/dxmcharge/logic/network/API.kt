package com.dxm.dxmcharge.logic.network

import com.dxm.dxmcharge.logic.model.Country
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class API {

    interface AccountService{

        //注册
        @POST("ev/version/1.0.0/api/ocpp/api")
        fun register(@Body body: RequestBody):Call<HttpResult<String>>



        //获取国家
        @POST("ev/version/1.0.0/api/countryList")
        fun countryList(@Body body: RequestBody):Call<HttpResult<List<Country>>>


    }

}