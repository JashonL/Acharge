package com.charge.lib.util

import android.app.Application
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.charge.lib.R
import java.lang.ref.WeakReference

object ToastUtil {

    private var app: Application? = null

    fun init(app: Application) {
        this.app = app
    }

    private var toast: WeakReference<Toast>? = null

    private fun cancelExist() {
        toast?.get()?.cancel()
    }


    fun show(content: String?) {
        content?.let {
            cancelExist()
            val showToast = Toast.makeText(app, content, Toast.LENGTH_LONG)
            showToast.setGravity(Gravity.CENTER, 0, 0)
            showToast.view?.setBackgroundResource(
                R.drawable.shape_toast_bg
            )
            showToast.view?.findViewById<TextView>(android.R.id.message)
                ?.setTextColor(ContextCompat.getColor(app!!.applicationContext, R.color.white))

            showToast.show()
            toast = WeakReference(showToast)
        }


    }


    fun showShortToast(content: String?) {
        content?.let {
            cancelExist()
            val showToast = Toast.makeText(app, content, Toast.LENGTH_SHORT)
            showToast.setGravity(Gravity.CENTER, 0, 0)
            showToast.show()
            toast = WeakReference(showToast)
        }
    }


}