package com.messapps.data.vmodelData.authentication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("accessToken")
    @Expose
    var accessToken: String? = null

    @SerializedName("sessionUser")
    @Expose
    var sessionUser: SessionUser? = SessionUser()

//For ResetPassword--------------

    @SerializedName("isSent")
    @Expose
    var isSent: Boolean? = null

    @SerializedName("timeout")
    @Expose
    var timeout: Int? = null

    @SerializedName("recoveryToken")
    @Expose
    var recoveryToken: String? = null

    @SerializedName("ok")
    @Expose
    var ok: Boolean? = null

    @SerializedName("isForceLogout")
    @Expose
    var isForceLogout: Boolean? = null

//-----------------------
}