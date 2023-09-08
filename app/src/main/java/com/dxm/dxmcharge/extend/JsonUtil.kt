package com.dxm.dxmcharge.extend

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody


fun <K, V> jsonOf(vararg pairs: Pair<K, V>): RequestBody =
    if (pairs.isNotEmpty()) {
        val toJson = Gson().toJson(mapOf(*pairs))
        RequestBody.create(
            "application/json;charset=utf-8".toMediaTypeOrNull(),toJson
           )
    } else throw Throwable("bad json arguments")