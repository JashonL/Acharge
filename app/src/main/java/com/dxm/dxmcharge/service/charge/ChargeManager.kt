package com.dxm.dxmcharge.service.charge

import com.charge.lib.storage.service.DefaultStorageService
import com.dxm.dxmcharge.App
import com.dxm.dxmcharge.logic.model.Charge

class ChargeManager private constructor() {

    private var chargeList = mutableListOf<Charge>()
    private var chargeModel: Charge? = null

    companion object {
        fun getInstance(): ChargeManager {
            return Holder.instance
        }
    }

    private object Holder {
        val instance = ChargeManager()
    }


    fun setChargeList(list: MutableList<Charge>) {
        this.chargeList = list
    }


    fun getChargeList() = chargeList


    fun setCurrentCharge(chargeModel: Charge?) {
        App.instance().storageService().put(DefaultStorageService.CURRENT_CHARGE, chargeModel?.chargeId)
        this.chargeModel = chargeModel
    }

    fun getCurrentCharge() = chargeModel

    interface ServiceInterface {

        fun setChargeList(list: MutableList<Charge>)

        fun getChargeList(): List<Charge>

        fun setCurrenChargeModel(chargeModel: Charge)

        fun getCurrentChargeModel(): Charge?

    }

}