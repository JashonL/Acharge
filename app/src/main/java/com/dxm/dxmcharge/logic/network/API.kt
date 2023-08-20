package com.dxm.dxmcharge.logic.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class API {

    interface AccountService{

        //注册
        @FormUrlEncoded
        @POST("/v2/user/register")
        fun register(@Field("cmd") cmd: String?,
                     @Field("userId") userId: String?,
                     @Field("phone") phone: String?,
                     @Field("password") password: String?,
                     @Field("email") email: String?,
                     @Field("installerId") installerId: String?,
                     @Field("zipCode") zipCode: String?,
                     @Field("country") country: String?,
                     @Field("city") city: String?,
                     @Field("carMode") carMode: String?,
                     @Field("car_1") car_1: String?,
                     @Field("car_2") car_2: String?,
                     @Field("lan") lan: String?,
                     ):Call<HttpResult<String>>


    }

}