package com.dxm.dxmcharge.logic.model

data class Recorder(
    val chargeId: String,
    val cid: Int,
    val connectorId: Int,
    val consume: Double,
    val cost: Int,
    val costenergy: Int,
    val costmoney: Int,
    val ctime: Int,
    val endtime: String,
    val energy: Double,
    val rate: Double,
    val starttime: String,
    val symbol: String,
    val sysEndTime: Long,
    val sysStartTime: Long,
    val transactionId: Int
)