package com.dxm.dxmcharge.logic.model

data class ChargingData(
    val Temperature: Double?,
    val chargeEndTime: String?,
    val chargeId: String?,
    val connectorId: Double?,
    val cost: Double?,
    val ctime: Int?,
    val ctype: Double?,
    val current: Double?,
    val energy: Double?,
    val online: Double?,
    val orderId: String?,
    val order_status: Double?,
    val rate: Double?,
    val status: String?,
    val symbol: String?,
    val sysEndTime: Long?,
    val sysStartTime: Long?,
    val transactionId: Double?,
    val unit: String?,
    val userId: String?,
    val voltage: Double?,
    val ckey:String?,
    val cValue: Double? = 0.0
)