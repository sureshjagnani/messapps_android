package com.messapps

import android.app.Application
import com.messapps.utils.GLOBAL_TAG
import com.messapps.utils.ThemeChanger
import com.messapps.utils.UserPreferencesRepository


class MyApp : Application() {

    lateinit var userPreferences: UserPreferencesRepository

    companion object {
        private val TAG: String =
            GLOBAL_TAG + " " + MyApp::class.java.simpleName

        @get:Synchronized
        lateinit var instance: MyApp private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        userPreferences = UserPreferencesRepository.getInstance(this)


        // if (BuildConfig.DEBUG) {
        //     Timber.plant(Timber.DebugTree())
        // }

        val theme = userPreferences.appTheme
        ThemeChanger().invoke(theme)
    }
}