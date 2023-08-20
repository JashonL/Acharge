package com.dxm.dxmcharge.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private val BASE_URL = "http://localhost/ocpp/api/"


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // val service = retrofit.create(service::class.java)  //每次都这样写的话会很麻烦，因此  可以用泛型来写成通用的方法
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

//使用泛型实例化 简化
    inline fun <reified T> create(): T = create(T::class.java)

}