package com.dxm.dxmcharge.ui.register

import android.view.animation.Transformation
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dxm.dxmcharge.logic.Respository

class RegisterModel : ViewModel() {

    private val registerLivedata = MutableLiveData<String>()


    private

    fun register(
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
        Respository.register(
            cmd,
            userId,
            phone,
            password,
            email,
            installerId,
            zipCode,
            country,
            city,
            carMode,
            car_1,
            car_2,
            lan
        )
    }


}