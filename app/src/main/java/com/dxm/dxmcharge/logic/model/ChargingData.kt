package com.dxm.dxmcharge.logic.model

data class ChargingData(
    val Temperature: Int?,
    val chargeEndTime: String?,
    val chargeId: String?,
    val connectorId: Int?,
    val cost: Int?,
    val ctime: Int?,
    val ctype: Int?,
    val current: Double?,
    val energy: Double?,
    val online: Int?,
    val orderId: String?,
    val order_status: Int?,
    val rate: Int?,
    val status: String?,
    val symbol: String?,
    val sysEndTime: Long?,
    val sysStartTime: Long?,
    val transactionId: Int?,
    val unit: String?,
    val userId: String?,
    val voltage: Double?
)