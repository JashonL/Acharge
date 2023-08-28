package com.dxm.dxmcharge.logic.network

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class API {

    interface AccountService{

        //注册
        @FormUrlEncoded
        @POST("/v2/user/register")
        fun register(@Body body: RequestBody):Call<HttpResult<String>>


    }

}