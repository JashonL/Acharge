package com.dxm.dxmcharge.ui.device

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.dxm.dxmcharge.base.BaseViewModel
import com.dxm.dxmcharge.extend.jsonOf
import com.dxm.dxmcharge.logic.Respository
import okhttp3.RequestBody

class AddChargeViewModel : BaseViewModel() {


    private val addLivedata = MutableLiveData<RequestBody>()
    fun addChage(
        cmd: String?,
        userId: String?,
        chargeId: String? = "",
        lan: String?,
    ) {
        val jsonOf = jsonOf(
            "cmd" to cmd,
            "userId" to userId,
            "chargeId" to chargeId,
            "lan" to lan
        )
        addLivedata.value = jsonOf
    }

    val newLiveData = addLivedata.switchMap {
        Respository.addCharge(it)
    }



}