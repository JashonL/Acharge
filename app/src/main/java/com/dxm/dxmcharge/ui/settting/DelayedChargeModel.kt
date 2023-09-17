package com.dxm.dxmcharge.ui.settting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dxm.dxmcharge.extend.jsonOf
import com.dxm.dxmcharge.logic.Respository
import okhttp3.RequestBody

class DelayedChargeModel : ViewModel() {

    private val delayedLivedata = MutableLiveData<RequestBody>()
    fun getDelaycharge(
        cmd: String?,
        userId: String?,
        chargeId: String? = "",
        connectorId: String? = "",
        startTime: String? = "",
        isDefault: String? = "",
        isDelay: String? = "",
        lan: String?,
    ) {
        val jsonOf = jsonOf(
            "cmd" to cmd,
            "userId" to userId,
            "chargeId" to chargeId,
            "connectorId" to connectorId,
            "startTime" to startTime,
            "isDefault" to isDefault,
            "isDelay" to isDelay,
            "lan" to lan
        )
        delayedLivedata.value = jsonOf
    }

    val newLiveData = delayedLivedata.switchMap {
        Respository.delayStartTransaction(it)
    }


}