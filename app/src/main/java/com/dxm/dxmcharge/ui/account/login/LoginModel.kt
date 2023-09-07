package com.dxm.dxmcharge.ui.account.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dxm.dxmcharge.extend.jsonOf
import com.dxm.dxmcharge.logic.Respository
import okhttp3.RequestBody

class LoginModel : ViewModel() {

    private val loginLivedata = MutableLiveData<RequestBody>()
    fun login(
        cmd: String?,
        userId: String?,
        mac: String?,
        password: String?,
        version: String?,
        lan: String?,
    ) {
        val jsonOf = jsonOf(
            "cmd" to cmd,
            "userId" to userId,
            "password" to password,
            "mac" to mac,
            "version" to version,
            "lan" to lan
        )
        loginLivedata.value = jsonOf
    }

    val newLoginLiveData = loginLivedata.switchMap {
        Respository.login(it)
    }


}