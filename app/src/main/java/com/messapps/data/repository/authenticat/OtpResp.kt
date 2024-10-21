package com.messapps.data.repository.authenticat

import com.messapps.data.vmodelData.ApiError
import com.messapps.data.vmodelData.authentication.LoginResponse
import com.messapps.data.vmodelData.authentication.ResetPasswordOtp


interface OtpResp {
    fun onSuc(response: ResetPasswordOtp?, mFrom:Int)
    fun onFail(data: ApiError?)
    fun onFailRes(msg: String?)
}
