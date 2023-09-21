package com.dxm.dxmcharge.ui.charge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.dxm.dxmcharge.base.BaseViewModel
import com.dxm.dxmcharge.extend.jsonOf
import com.dxm.dxmcharge.logic.Respository
import okhttp3.RequestBody

class ChargePresetViewModel : BaseViewModel() {



    private val reserveNowLivedata = MutableLiveData<RequestBody>()
    fun reserveNow(
        cmd: String?,
        userId: String?,
        chargeId: String? = "",
        connectorId: String? = "",
        lan: String?,
    ) {



        val jsonOf = jsonOf(
            "cmd" to cmd,
            "userId" to userId,
            "chargeId" to chargeId,
            "connectorId" to connectorId,
            "lan" to lan
        )
        reserveNowLivedata.value = jsonOf
    }

    val newLiveData = reserveNowLivedata.switchMap {
        Respository.reserveNow(it)
    }








}