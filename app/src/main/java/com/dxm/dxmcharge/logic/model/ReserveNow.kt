package com.dxm.dxmcharge.logic.model

 data class ReserveNow(
    val action: Int,
    val cKey: String,
    val cValue: Int,
    val cValue2: String,
    val chargeId: String,
    val cid: Int,
    val connectorId: Int,
    val cost: Int,
    val ctime: Long,
    val endDate: String,
    val expiryDate: String,
    val loopType: Int,
    val loopValue: String,
    val msgId: String,
    val rate: Int,
    val reservationId: Int,
    val status: String,
    val transactionId: Int,
    val userId: String
)