package com.dxm.dxmcharge.ui.device

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dxm.dxmcharge.extend.jsonOf
import com.dxm.dxmcharge.logic.Respository
import okhttp3.RequestBody

class ChargeListModel : ViewModel() {

    private val chargelistLivedata = MutableLiveData<RequestBody>()
    fun getChargelist(
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
        chargelistLivedata.value = jsonOf
    }

    val newLiveData = chargelistLivedata.switchMap {
        Respository.getChargeList(it)
    }


}