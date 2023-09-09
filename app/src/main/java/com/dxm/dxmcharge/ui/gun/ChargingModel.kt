package com.dxm.dxmcharge.ui.gun

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dxm.dxmcharge.extend.jsonOf
import com.dxm.dxmcharge.logic.Respository
import okhttp3.RequestBody

class ChargingModel : ViewModel() {

    private val chargingLivedata = MutableLiveData<RequestBody>()



    fun getChargeInfo(
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
            "lan" to lan,
        )
        chargingLivedata.value = jsonOf
    }

    val newLiveData = chargingLivedata.switchMap {
        Respository.getChargingData(it)
    }


}