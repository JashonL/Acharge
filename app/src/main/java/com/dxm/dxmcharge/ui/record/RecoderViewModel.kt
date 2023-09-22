package com.dxm.dxmcharge.ui.record

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.dxm.dxmcharge.base.BaseViewModel
import com.dxm.dxmcharge.extend.jsonOf
import com.dxm.dxmcharge.logic.Respository
import okhttp3.RequestBody

class RecoderViewModel : BaseViewModel() {


    private val recorderLivedata = MutableLiveData<RequestBody>()
    fun chargeRecorder(
        cmd: String?,
        chargeId: String?,
        page: String? = "",
        pageSize: String? = "",
        lan: String?,
        userId: String?,
    ) {
        val jsonOf = jsonOf(
            "cmd" to cmd,
            "chargeId" to chargeId,
            "page" to page,
            "pageSize" to pageSize,
            "lan" to lan,
            "userId" to userId
        )
        recorderLivedata.value = jsonOf
    }

    val newLiveData = recorderLivedata.switchMap {
        Respository.getRecorder(it)
    }



}