package com.messapps.data.vmodelData.authentication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResetPasswordOtp {
    @SerializedName("recoveryToken")
    @Expose
    var recoveryToken: String? = null

    @SerializedName("sessionUser")
    @Expose
    var sessionUser: SessionUser? = SessionUser()

    @SerializedName("ok")
    @Expose
    var ok: Boolean? = null

    @SerializedName("isForceLogout")
    @Expose
    var isForceLogout: Boolean? = null
}