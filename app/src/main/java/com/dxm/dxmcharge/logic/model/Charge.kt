package com.dxm.dxmcharge.logic.model

data class Charge(
    val G_LCDCloseEnable: String,
    val G_SolarLimitPower: Double,
    val G_SolarMode: Int,
    val chargeId: String,
    val cid: Int,
    val code: String,
    val connectors: Int,
    val elockstate: String,
    val ip: String,
    val model: String,
    val name: String,
    val rate: Double,
    val sn_connectors: Int,
    val sn_model: String,
    val solar: Int,
    val spec: String,
    val time: Long,
    val type: Int,
    val userId: String,
    val userPhone: String
)