package com.shuoxd.lib.service.account

/**
 * 用户ID
 * 用户名
 * 手机号码
 * 安装商编号
 * 邮件
 * token
 */
data class User(
    val addr: String?,
    val agent: String?,
    val area: String?,
    val areaId: Int?,
    val auth: Int?,
    val cid: Int?,
    val city: String?,
    val code: String?,
    val country: String?,
    val email: String?,
    val name: String?,
    val nick: String?,
    val parentId: String?,
    val phone: String?,
    val provice: String?,
    val registTime: String?,
    val roleId: String?,
    val roleName: String?,
    val status: Int?,
    val time: Long?,
    val type: Int?,
    val userId: String?,
    val zipCode: String?,
    var password:String,
)