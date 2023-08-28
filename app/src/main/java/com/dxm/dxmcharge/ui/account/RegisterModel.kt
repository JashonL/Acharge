package com.dxm.dxmcharge.ui.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dxm.dxmcharge.extend.jsonOf
import com.dxm.dxmcharge.logic.Respository
import okhttp3.RequestBody

class RegisterModel : ViewModel() {

    private val registerLivedata = MutableLiveData<RequestBody>()


    private fun toRegister(
        cmd: String?,
        userId: String?,
        phone: String?,
        password: String?,
        email: String?,
        installerId: String?,
        zipCode: String?,
        country: String?,
        city: String?,
        carMode: String?,
        car_1: String?,
        car_2: String?,
        lan: String?
    ) {
        val jsonOf = jsonOf(
            "cmd" to cmd,
            "userId" to userId,
            "phone" to phone,
            "password" to password,
            "email" to email,
            "installerId" to installerId,
            "zipCode" to zipCode,
            "country" to country,
            "city" to city,
            "carMode" to carMode,
            "car_1" to car_1,
            "car_2" to car_2,
            "lan" to lan
        )

        registerLivedata.value = jsonOf
    }

    val newLiveData = registerLivedata.switchMap {
        Respository.register(it)
    }


}