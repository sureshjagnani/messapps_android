package com.messapps.utils

import android.content.Context
import com.google.gson.Gson
import com.messapps.data.vmodelData.authentication.SessionUser
import java.util.*


class PreferencesUtils (context: Context){

    private val sharedPreferences =
        context.applicationContext.getSharedPreferences(APP_SETTINGS_PREF, Context.MODE_PRIVATE)
    /**
     * Get the appTheme. By default, theme is set to follow.
     */
//    val appTheme: Theme
//        get() {
//            val theme = sharedPreferences.getString(PREF_NAME_THEME_MODE, Theme.LIGHT_MODE.name)
//            return Theme.valueOf(theme ?: Theme.LIGHT_MODE.name)
//        }
//
//    fun updateTheme(theme: Theme) {
//        sharedPreferences.edit()
//            .putString(PREF_NAME_THEME_MODE, theme.name)
//            .apply()
//
//        ThemeChanger().invoke(theme)
//    }
    fun setLogin(mLogin: Boolean) {
        sharedPreferences.edit()
            .putBoolean(LOGIN_PREF, mLogin)
            .apply()
    }
    fun getLogin(): Boolean {
        return sharedPreferences.getBoolean(LOGIN_PREF, false)
    }
    fun setSessionToken(token: String) {
        sharedPreferences.edit()
            .putString(PREF_TOKEN, token)
            .apply()
    }
    fun getSessionToken(): String {
        return sharedPreferences.getString(PREF_TOKEN, "0")!!
    }
    fun setAccessToken(token: String) {
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN, token)
            .apply()
    }
    fun getAccessToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN, "0")!!
    }

    fun setUserInfo(mInfo: SessionUser) {
        val strObject: String = Gson().toJson(mInfo, SessionUser::class.java)
        sharedPreferences.edit().putString(SESSION_INFO, strObject).apply()

    }

    fun getUserInfo(): SessionUser {
        val mPojo: SessionUser
        val json: String = sharedPreferences.getString(SESSION_INFO, "")!!
        if (json.isNotEmpty()) mPojo = Gson().fromJson(json, SessionUser::class.java)
        else mPojo = SessionUser()

        return mPojo
    }
    //    public List<SearchData> getSearchInfo() {
    //
    //        List<SearchData> mPojo;
    //        Type type = new TypeToken<List<SearchData>>() {
    //        }.getType();
    //        String json = sharePref.getString("mSearchData", "");
    //        if (json.length() > 0)
    //            mPojo = new Gson().fromJson(json, type);
    //        else
    //            mPojo = new ArrayList<>();
    //
    //        return mPojo;
    //    }
    //    public void setBillsInfo(List<Datum> mInfo) {
    //        String strObject = new Gson().toJson(mInfo);
    //        editor.putString("mBillsData", strObject);
    //        editor.commit();
    //    }
    //


    companion object {
        @Volatile
        private var INSTANCE: PreferencesUtils? = null

        fun getInstance(context: Context): PreferencesUtils {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }
                val instance = PreferencesUtils(context)
                INSTANCE = instance
                instance
            }
        }
    }

}