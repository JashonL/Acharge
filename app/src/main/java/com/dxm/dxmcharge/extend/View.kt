package com.dxm.dxmcharge.extend

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

fun View.visible() {
    visibility = View.VISIBLE
}


fun View.invisible(){
    visibility = View.INVISIBLE
}



fun View.gone() {
    visibility = View.VISIBLE
}


/**
 * 设置TextView左边Icon
 */
fun TextView.setDrawableStart(drawable: Drawable?) {
    setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
}

fun TextView.setDrawableEnd(drawable: Drawable) {
    setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
}

fun TextView.setDrawableNull() {
    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
}


fun TextView.textColor(colorRes: Int) {
    setTextColor(ContextCompat.getColor(context, colorRes))
}