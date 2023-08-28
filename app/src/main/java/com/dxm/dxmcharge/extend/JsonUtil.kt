package com.dxm.dxmcharge.extend

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.RequestBody

fun <K, V> jsonOf(vararg pairs: Pair<K, V>): RequestBody =
    if (pairs.isNotEmpty()){
        FormBody .create(MediaType.parse("application/json;charset=utf-8"),Gson().toJson(mapOf(*pairs)))
    }
    else throw Throwable("bad json arguments")