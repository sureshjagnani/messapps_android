package com.messapps.data.repository.authenticat

import com.google.gson.Gson
import com.messapps.data.service.RetrofitInterface
import com.messapps.data.vmodelData.ApiError
import com.messapps.data.vmodelData.authentication.LoginResponse
import com.messapps.data.vmodelData.authentication.ResetPasswordOtp
import com.messapps.utils.COMMON_MSG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpMapper(private val view: OtpResp, private val retrofitInterface: RetrofitInterface)
{
    fun otpVerify(headers: HashMap<String, String>, data: HashMap<String, String>,mFrom:Int) {
        retrofitInterface.getResetPasswordOtp(headers, data)
            .enqueue(object : Callback<ResetPasswordOtp?> {
                override fun onResponse(
                    call: Call<ResetPasswordOtp?>,
                    response: Response<ResetPasswordOtp?>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            view.onSuc(response.body(),mFrom)
                        } else {
                            view.onFailRes(COMMON_MSG)
                        }
                    } else {
                        if (response.code() >= 400) {
                            try {
                                val gson = Gson()
                                val message = gson.fromJson(
                                    response.errorBody()!!.charStream(),
                                    ApiError::class.java
                                )
                                view.onFail(message)
                            } catch (e: Exception) {
                                e.printStackTrace()
                                view.onFailRes(COMMON_MSG)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResetPasswordOtp?>, t: Throwable) {
                    view.onFailRes(COMMON_MSG)
                }
            })
    }
    fun verifyOtpCode(headers: HashMap<String, String>, data: HashMap<String, String>,mFrom:Int) {
        retrofitInterface.getVerifyOtpCode(headers, data)
            .enqueue(object : Callback<ResetPasswordOtp?> {
                override fun onResponse(
                    call: Call<ResetPasswordOtp?>,
                    response: Response<ResetPasswordOtp?>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            view.onSuc(response.body(),mFrom)
                        } else {
                            view.onFailRes(COMMON_MSG)
                        }
                    } else {
                        if (response.code() >= 400) {
                            try {
                                val gson = Gson()
                                val message = gson.fromJson(
                                    response.errorBody()!!.charStream(),
                                    ApiError::class.java
                                )
                                view.onFail(message)
                            } catch (e: Exception) {
                                e.printStackTrace()
                                view.onFailRes(COMMON_MSG)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResetPasswordOtp?>, t: Throwable) {
                    view.onFailRes(COMMON_MSG)
                }
            })
    }
    fun recoverPassword(headers: HashMap<String, String>, data: HashMap<String, String>,mFrom:Int) {
        retrofitInterface.getRecoverPassword(headers, data)
            .enqueue(object : Callback<ResetPasswordOtp?> {
                override fun onResponse(
                    call: Call<ResetPasswordOtp?>,
                    response: Response<ResetPasswordOtp?>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            view.onSuc(response.body(),mFrom)
                        } else {
                            view.onFailRes(COMMON_MSG)
                        }
                    } else {
                        if (response.code() >= 400) {
                            try {
                                val gson = Gson()
                                val message = gson.fromJson(
                                    response.errorBody()!!.charStream(),
                                    ApiError::class.java
                                )
                                view.onFail(message)
                            } catch (e: Exception) {
                                e.printStackTrace()
                                view.onFailRes(COMMON_MSG)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResetPasswordOtp?>, t: Throwable) {
                    view.onFailRes(COMMON_MSG)
                }
            })
    }
}
