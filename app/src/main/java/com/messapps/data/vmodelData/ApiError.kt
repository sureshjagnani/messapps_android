package com.messapps.data.vmodelData

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ApiError : Serializable {
    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("errorCode")
    @Expose
    var errorCode: Int? = null


    @SerializedName("isForceLogout")
    @Expose
    var isForceLogout: Boolean? = null
}
