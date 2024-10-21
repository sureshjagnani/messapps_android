package com.messapps.data.service

import com.messapps.data.vmodelData.authentication.LoginResponse
import com.messapps.data.vmodelData.authentication.ResetPasswordOtp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST


interface RetrofitInterface {
    @POST("/api/auth/sign-in")
    fun getLogin(
        @HeaderMap headers: HashMap<String, String>,
        @Body json: HashMap<String, String>
    ): Call<LoginResponse>

    @POST("/api/auth/sign-up")
    fun getSignUp(
        @HeaderMap headers: HashMap<String, String>,
        @Body json: HashMap<String, String>
    ): Call<LoginResponse>

    @POST("/api/auth/reset-password")
    fun getResetPassword(
        @HeaderMap headers: HashMap<String, String>,
        @Body json: HashMap<String, String>
    ): Call<LoginResponse>

    @POST("/api/auth/verify-reset-password-otp")
    fun getResetPasswordOtp(
        @HeaderMap headers: HashMap<String, String>,
        @Body json: HashMap<String, String>
    ): Call<ResetPasswordOtp>

    @POST("/api/verification/verify-otp-code")
    fun getVerifyOtpCode(
        @HeaderMap headers: HashMap<String, String>,
        @Body json: HashMap<String, String>
    ): Call<ResetPasswordOtp>

    @POST("/api/auth/recover-password")
    fun getRecoverPassword(
        @HeaderMap headers: HashMap<String, String>,
        @Body json: HashMap<String, String>
    ): Call<ResetPasswordOtp>

     //    @POST("api/v2/sewadars/search")
    //    Call<Member> getMembers(@HeaderMap HashMap<String, String> headers, @Body HashMap<String, String> json);
    //
    //    @POST("api/v2/sewadars/departments/get")
    //    Call<Member> getDepart(@HeaderMap HashMap<String, String> headers, @Body HashMap<String, String> json);
    //
    //    @POST("api/v2/sewadars/attendance/get")
    //    Call<Attendance> getAttendance(@HeaderMap HashMap<String, String> headers, @Body HashMap<String, String> json);
    //
    //    @POST("api/v2/sewadars/get")
    //    Call<MemberDetail> getMemberDetail(@HeaderMap HashMap<String, String> headers, @Body HashMap<String, String> json);
}
