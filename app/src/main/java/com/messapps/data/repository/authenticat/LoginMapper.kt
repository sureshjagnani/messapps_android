package com.messapps.data.repository.authenticat

import com.google.gson.Gson
import com.messapps.data.service.RetrofitInterface
import com.messapps.data.vmodelData.ApiError
import com.messapps.data.vmodelData.authentication.LoginResponse
import com.messapps.utils.COMMON_MSG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginMapper(private val view: LoginResp, private val retrofitInterface: RetrofitInterface)
{
    fun loginEvent(headers: HashMap<String, String>, data: HashMap<String, String>,mFrom:Int) {
        retrofitInterface.getLogin(headers, data)
            .enqueue(object : Callback<LoginResponse?> {
                override fun onResponse(
                    call: Call<LoginResponse?>,
                    response: Response<LoginResponse?>
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

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    view.onFailRes(COMMON_MSG)
                }
            })
    }
    fun signUpEvent(headers: HashMap<String, String>, data: HashMap<String, String>,mFrom:Int) {
        retrofitInterface.getSignUp(headers, data)
            .enqueue(object : Callback<LoginResponse?> {
                override fun onResponse(
                    call: Call<LoginResponse?>,
                    response: Response<LoginResponse?>
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

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    view.onFailRes(COMMON_MSG)
                }
            })
    }
    fun forgotPasswordEvent(headers: HashMap<String, String>, data: HashMap<String, String>, mFrom:Int) {
        retrofitInterface.getResetPassword(headers, data)
            .enqueue(object : Callback<LoginResponse?> {
                override fun onResponse(
                    call: Call<LoginResponse?>,
                    response: Response<LoginResponse?>
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

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    view.onFailRes(COMMON_MSG)
                }
            })
    }
}
