package com.dxm.dxmcharge.logic.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceCreator {

    private val BASE_URL = "https://blinkcharge.cn/"


    //配置okHttp并设置时间、日志信息和cookies


    //配置okHttp并设置时间、日志信息和cookies
    var httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level= HttpLoggingInterceptor.Level.BODY
    }



    var okHttpClient = OkHttpClient.Builder()
        .addInterceptor(RequestInterceptor())
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS) //设置Cookie持久化
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // val service = retrofit.create(service::class.java)  //每次都这样写的话会很麻烦，因此  可以用泛型来写成通用的方法
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

//使用泛型实例化 简化
    inline fun <reified T> create(): T = create(T::class.java)

}