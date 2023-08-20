package com.dxm.dxmcharge.logic.network

data class HttpResult<T>(var code: String, var type: String?, var data: T?)
