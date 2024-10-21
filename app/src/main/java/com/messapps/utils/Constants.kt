package com.messapps.utils

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import android.util.Patterns
import com.messapps.data.model.NavigationData
import com.messapps.data.model.estimates.EstimateData
import com.messapps.data.model.invoices.InvoicesData
import com.messapps.data.model.progress.ProgressData
import java.util.regex.Pattern


const val GLOBAL_TAG = "MessappsTag"
const val APP_SETTINGS_PREF = "SettingsPrefs"
const val PREF_NAME_THEME_MODE = "pref_theme_mode"
const val LOGIN_PREF = "mLoginPref"
const val PREF_TOKEN = "MessappsSessionToken"
const val ACCESS_TOKEN = "accessToken"
const val SESSION_INFO = "sessionInfo"
const val mLogin = true
const val COMMON_URL = "https://app-api.staging.messapps.com"
const val COMMON_MSG = "Something went wrong. Please contact administrator and try again later."
var Last_Selection = 0
var CALL_FROM = 0
var Call_Profile = 3
var Call_Contact = 4
var mEmail = ""
var mRToken = ""
const val ARG_PARAM1 = "param1"
const val ARG_PARAM2 = "param2"
const val progressDialogMsg = "please wait.."
const val INTERNET_NOT_AVAILABLE = "Please check your internet connection and try again."
const val INVALID_CREDENTIALS_MSG = "Invalid credentials and/or password."
const val OTP_MSG = "Please enter proper OTP code"
const val EMAIL_MSG = "Please enter email address and try again!"
const val EMAIL_FAIL_MSG = "Please check your email address and try again!"
const val PASSWORD_FAIL_MSG = "Your password must be at least 8 characters long, contain at least one special character and have a mixture of uppercase and lowercase letters."
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun isValidEmail(emailId: String?): Boolean {
    return (!TextUtils.isEmpty(emailId) && Patterns.EMAIL_ADDRESS.matcher(emailId).matches())
}
fun isValidPassword(password: String?): Boolean
{
    val PASSWORD_PATTERN =
        Pattern.compile("^" +
                "(?=.*[@#$%^&+=])" +     // at least 1 special character
                "(?=\\S+$)" +            // no white spaces
                ".{8,}" +               // at least 8 characters
                ".*[A-Z].*"+            //uppser case
                ".*[a-z].*"+            //lowercase
                "$")
    return (!TextUtils.isEmpty(password) && PASSWORD_PATTERN.matcher(password).matches())
}
fun showProgressBar(msg: String?, context: Context): ProgressDialog {
    var progressDialog = ProgressDialog(context)
    progressDialog.setCancelable(false) //you can cancel it by pressing back button
    progressDialog.setMessage(msg)
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    return progressDialog
}

fun hideProgressBar(progressDialog: ProgressDialog) {
    try {
        if (progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    } catch (ex: Exception) {
        //Log.e(TAG, "Exception in hideProgressDialog() " + ex.getMessage());
    }
}

fun getData(): HashMap<String, List<NavigationData>> {
    val expandableListDetail = HashMap<String, List<NavigationData>>()

    val cp: MutableList<NavigationData> = ArrayList()
    cp.add(NavigationData("Home", 1))
    cp.add(NavigationData("Estimates", 2))
    cp.add(NavigationData("Progress", 3))
    cp.add(NavigationData("View Builds", 4))
    cp.add(NavigationData("Invoices", 5))
    val ap: MutableList<NavigationData> = ArrayList()

    val ma: MutableList<NavigationData> = ArrayList()
    ma.add(NavigationData("About us", 6))
    ma.add(NavigationData("Blog", 7))
    ma.add(NavigationData("Press", 8))
    ma.add(NavigationData("Awards", 9))
    ma.add(NavigationData("Social", 10))
    ma.add(NavigationData("Website", 11))
    ma.add(NavigationData("Reviews", 12))

    val profile: MutableList<NavigationData> = ArrayList()
    val contact: MutableList<NavigationData> = ArrayList()

    expandableListDetail["Client Portal"] = cp
    expandableListDetail["Affiliate Portal"] = ap
    expandableListDetail["MessApps"] = ma
    expandableListDetail["Profile"] = profile
    expandableListDetail["Contact"] = contact
    return expandableListDetail
}

fun getTitle(): MutableList<NavigationData> {
    //val expandableListDetail: MutableList<NavigationData> = mutableListOf()

    val cp: MutableList<NavigationData> = mutableListOf()
    cp.add(NavigationData("Client Portal", 1))
    cp.add(NavigationData("Affiliate Portal", 2))
    cp.add(NavigationData("MessApps", 3))
    cp.add(NavigationData("Profile", 4))
    cp.add(NavigationData("Contact", 5))

    return cp
}

fun getEstimate(): MutableList<EstimateData> {
    //val expandableListDetail: MutableList<NavigationData> = mutableListOf()

    val cp: MutableList<EstimateData> = mutableListOf()
    cp.add(EstimateData("Project A1", "90", "$10,000"))
    cp.add(EstimateData("Project A2", "120", "$13,500"))
    cp.add(EstimateData("Project B", "100", "$8,000"))

    return cp
}

fun getProgress(): MutableList<ProgressData> {

    val cp: MutableList<ProgressData> = mutableListOf()
    cp.add(ProgressData("Project A1", "90", "$10,000"))
    cp.add(ProgressData("Project A2", "120", "$13,500"))
    cp.add(ProgressData("Project B", "100", "$8,000"))

    return cp
}

fun getInvoices(): MutableList<InvoicesData> {

    val cp: MutableList<InvoicesData> = mutableListOf()
    cp.add(InvoicesData("June 2024", false, false))
    cp.add(InvoicesData("July 2024", false, false))
    cp.add(InvoicesData("Aug 2024", false, false))

    return cp
}