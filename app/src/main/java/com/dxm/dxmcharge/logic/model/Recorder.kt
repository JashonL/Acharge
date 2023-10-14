package com.dxm.dxmcharge.logic.model

data class Recorder(
    val chargeId: String?,
    val chargemode: Int?,
    val cid: Int?,
    val connectorId: Int?,
    val consume: Int?,
    val cost: Int?,
    val ctime: Int?,
    val endtime: String?,
    val energy: Double?,
    val pvEnergy: Int?,
    val rate: Double?,
    val starttime: String?,
    val status: String?,
    val sysEndTime: Long?,
    val sysStartTime: Long?,
    val userId: String?,
    val workmode: Int?
)