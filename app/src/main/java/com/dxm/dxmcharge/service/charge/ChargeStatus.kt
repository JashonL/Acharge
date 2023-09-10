package com.dxm.dxmcharge.service.charge

import androidx.annotation.IntDef
import androidx.annotation.StringDef
import com.dxm.dxmcharge.App
import com.dxm.dxmcharge.R

/**
 * 电枪状态
 */

@StringDef(
    ChargeStatus.UNAVAILABLE,
    ChargeStatus.SUSPENDEDEV,
    ChargeStatus.AVAILABLE,
    ChargeStatus.SUSPENDEDEVSE,
    ChargeStatus.CHARGING,
    ChargeStatus.PREPARING,
    ChargeStatus.FINISHING,
    ChargeStatus.FAULTED,
    ChargeStatus.RESERVENOW,
)


@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class ChargeStatus {
    /*
    	Unavailable=不在线
SuspendedEV=拒绝充电
Available=可用电桩
SuspendedEVSE=急停按下
Charging=正在充电
Preparing=准备充电
Finishing=充电完成
Faulted=电桩故障
ReserveNow=正在预约
    */
    companion object {
        const val UNAVAILABLE = "Unavailable"
        const val SUSPENDEDEV = "SuspendedEV"
        const val AVAILABLE = "Available"
        const val SUSPENDEDEVSE = "SuspendedEVSE"
        const val CHARGING = "Charging"
        const val PREPARING = "Preparing"
        const val FINISHING = "Finishing"
        const val FAULTED = "Faulted"
        const val RESERVENOW = "ReserveNow"

        fun getChargeStatus(@ChargeStatus status: String?): String {
            return App.instance().getString(
                when (status) {
                    UNAVAILABLE -> R.string.m93_unavailable
                    SUSPENDEDEV -> R.string.suspendedev
                    AVAILABLE -> R.string.m94_available
                    SUSPENDEDEVSE -> R.string.suspendedevse
                    CHARGING -> R.string.m96_charging
                    PREPARING -> R.string.m95_prepear
                    FINISHING -> R.string.m98_charge_finish
                    FAULTED -> R.string.faulted
                    RESERVENOW -> R.string.reservenow
                    else -> R.string.m101_fault
                }
            )
        }

    }


}