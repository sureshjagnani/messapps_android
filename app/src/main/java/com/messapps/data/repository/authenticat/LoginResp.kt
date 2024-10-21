package com.messapps.data.repository.authenticat

import com.messapps.data.vmodelData.ApiError
import com.messapps.data.vmodelData.authentication.LoginResponse


interface LoginResp {
    fun onSuc(response: LoginResponse?,mFrom:Int)
    fun onFail(data: ApiError?)
    fun onFailRes(msg: String?)
}
