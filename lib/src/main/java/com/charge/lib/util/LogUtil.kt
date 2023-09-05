package com.charge.lib.util

import android.util.Log
import com.charge.lib.BuildConfig

object LogUtil {

    fun i(tag: String, log: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, log)
        }
    }
}