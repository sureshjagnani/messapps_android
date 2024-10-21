package com.messapps.data.vmodelData.authentication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SessionUser {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("role")
    @Expose
    var role: String? = null

    @SerializedName("sessionToken")
    @Expose
    var sessionToken: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("isEmailVerified")
    @Expose
    var isEmailVerified: Boolean? = null

    @SerializedName("isTermsAndPolicyAccepted")
    @Expose
    var isTermsAndPolicyAccepted: Boolean? = null

}