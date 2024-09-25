package com.messapps

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.messapps.utils.UserPreferencesRepository
import com.messapps.utils.Theme

class SplashView : AppCompatActivity()
{
    private var themePosition : Int? = null
    private var arrayTheme : Array<String>? = null

    private var userPrefs : UserPreferencesRepository = MyApp.instance.userPreferences

    //private lateinit var btnSwitch: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.splash_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initTheme(){
        themePosition = when (userPrefs.appTheme) {
            Theme.LIGHT_MODE -> 0
            Theme.DARK_MODE -> 1
            else -> 2
        }

        //btnSwitch.text = arrayTheme!![themePosition!!]
    }

    private fun setAppTheme(themePosition:Int){

        //btnSwitch.text = arrayTheme!![themePosition!!]

        userPrefs.updateTheme(
            when (themePosition) {
                0 -> Theme.LIGHT_MODE
                1 -> Theme.DARK_MODE
                else -> Theme.LIGHT_MODE
            }
        )

    }


//    private fun showThemeDialog(){
//
//        //Timber.tag(TAG).d("showThemeDialog")
//
//        MaterialAlertDialogBuilder(this)
//            .setTitle(R.string.label_select_theme)
//            .setSingleChoiceItems(R.array.themes, themePosition!!) { _, i ->
//
//                themePosition = i
//
//                Timber.tag(TAG).d("Theme selected Pos : $themePosition")
//
//                setTheme()
//
//            }.show()
//
//    }

//    companion object {
//        private val TAG: String =
//            GLOBAL_TAG + " " + MainActivity::class.java.simpleName
//    }
}