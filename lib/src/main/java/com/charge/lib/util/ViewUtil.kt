package com.shuoxd.lib.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.widget.NumberPicker
import androidx.annotation.ColorInt

object ViewUtil {

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resId > 0) {
            result = context.resources.getDimensionPixelOffset(resId)
        }
        return result
    }

    fun getActivityFromContext(context: Context): Activity? {
        if (context is Activity) {
            return context
        }
        return if (context is ContextWrapper) {
            getActivityFromContext(context.baseContext)
        } else null
    }

    fun dp2px(context: Context?, dipValue: Float): Int {
        if (context == null) {
            return dipValue.toInt()
        }
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    fun px2dp(context: Context?, pxValue: Float): Int {
        if (context == null) {
            return pxValue.toInt()
        }
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 解决NumberPicker初始化不显示文案问题
     * https://issuetracker.google.com/issues/36952035?pli=1
     */
    fun fixNumberPicker(numberPicker: NumberPicker) {
        try {
            val method =
                numberPicker::class.java.getDeclaredMethod("changeValueByOne", Boolean::class.java)
            method.isAccessible = true
            method.invoke(numberPicker, true)
        } catch (e: Exception) {
        }
    }




}