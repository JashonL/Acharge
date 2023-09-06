package com.tianji.ttech.base

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.charge.lib.storage.account.IAccountService
import com.charge.lib.storage.service.IDeviceService
import com.charge.lib.storage.service.IStorageService
import com.charge.lib.storage.service.ServiceManager
import com.dxm.dxmcharge.App
import com.dxm.dxmcharge.base.BaseActivity

open class BaseViewHolder(
    itemView: View,
    private val onItemClickListener: OnItemClickListener? = null
) : RecyclerView.ViewHolder(itemView),
    ServiceManager.ServiceInterface, View.OnClickListener, View.OnLongClickListener {

    fun showDialog() {
        (itemView.context as? BaseActivity)?.showDialog()
    }

    fun dismissDialog() {
        (itemView.context as? BaseActivity)?.dismissDialog()
    }



    override fun storageService(): IStorageService {
        return App.instance().storageService()
    }

    override fun deviceService(): IDeviceService {
        return App.instance().deviceService()
    }

    override fun accountService(): IAccountService {
        return App.instance().accountService()
    }



    override fun onClick(v: View?) {
        onItemClickListener?.onItemClick(v, bindingAdapterPosition)
    }

    fun getColor(@ColorRes colorId: Int): Int {
        return App.instance().resources.getColor(colorId)
    }

    fun getString(@StringRes stringId: Int): String {
        return App.instance().getString(stringId)
    }

    override fun onLongClick(v: View?): Boolean {
        onItemClickListener?.onItemLongClick(v, bindingAdapterPosition)
        return true
    }



}

interface OnItemClickListener {

    fun onItemClick(v: View?, position: Int) {}

    fun onItemLongClick(v: View?, position: Int) {}
}