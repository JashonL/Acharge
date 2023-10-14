package com.dxm.dxmcharge.ui.gun

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dxm.dxmcharge.extend.jsonOf
import com.dxm.dxmcharge.logic.Respository
import com.dxm.dxmcharge.service.charge.ChargeStatus
import okhttp3.RequestBody

class ChargingModel : ViewModel() {

    private val chargingLivedata = MutableLiveData<RequestBody>()

    var status: String = ChargeStatus.UNAVAILABLE

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



    private val startLivedata = MutableLiveData<RequestBody>()
    fun charge(
        cmd: String?,
        userId: String?,
        chargeId: String? = "",
        connectorId: String? = "",
        lan: String?,
    ) {
        val jsonOf = jsonOf(
            "action" to cmd,
            "userId" to userId,
            "chargeId" to chargeId,
            "connectorId" to connectorId,
            "lan" to lan,
        )
        startLivedata.value = jsonOf
    }


    val actionLiveData = startLivedata.switchMap {
        Respository.action(it)
    }





    private val lockLivedata = MutableLiveData<RequestBody>()
    fun unlock(
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
        lockLivedata.value = jsonOf
    }


    val unlockLiveData = lockLivedata.switchMap {
        Respository.unlock(it)
    }


}