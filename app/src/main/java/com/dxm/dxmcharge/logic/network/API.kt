package com.dxm.dxmcharge.logic.network

import com.dxm.dxmcharge.logic.model.Charge
import com.dxm.dxmcharge.logic.model.ChargingData
import com.dxm.dxmcharge.logic.model.Country
import com.shuoxd.lib.service.account.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class API {

    interface AccountService{

        //注册
        @POST("ev/version/1.0.0/api/register")
        fun register(@Body body: RequestBody):Call<HttpResult<String>>

        //登录
        @POST("ev/version/1.0.0/api/login")
        fun login(@Body body: RequestBody):Call<HttpResult<User>>

        //获取国家
        @POST("ev/version/1.0.0/api/countryList")
        fun countryList(@Body body: RequestBody):Call<HttpResult<List<Country>>>


        //获取充电桩
        @POST("ev/version/1.0.0/api/list")
        fun chargeList(@Body body: RequestBody):Call<HttpResult<List<Charge>>>

        //添加充电桩
        @POST("ev/version/1.0.0/api/add")
        fun addCharge(@Body body: RequestBody):Call<HttpResult<String>>


        //获取充电数据
        @POST("/ev/version/1.0.0/charge/info/")
        fun charge(@Body body: RequestBody):Call<HttpResult<ChargingData>>



        //开始充电
        @POST("/ev/version/1.0.0/cmd/action/")
        fun action(@Body body: RequestBody):Call<HttpResult<String>>


    }

}