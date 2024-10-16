package com.messapps

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import com.messapps.authentication.login.LoginView
import com.messapps.databinding.SplashViewBinding
import com.messapps.utils.UserPreferencesRepository
import com.messapps.utils.Theme

class SplashView : AppCompatActivity() {
    private var themePosition: Int? = null

    private var userPrefs: UserPreferencesRepository = MyApp.instance.userPreferences
    private lateinit var llLight: LinearLayoutCompat
    private lateinit var llDark: LinearLayoutCompat
    private lateinit var binding: SplashViewBinding
    private val splashTimeOut  = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.llLight.setOnClickListener{
            setAppTheme(0)
        }
        binding.llDark.setOnClickListener{
            setAppTheme(1)
        }
        Handler().postDelayed({
            val i: Intent = Intent(this@SplashView, LoginView::class.java)
            startActivity(i)
            finish()
        }, splashTimeOut.toLong())
        initTheme()
    }

    private fun initTheme() {
        themePosition = when (userPrefs.appTheme) {
            Theme.LIGHT_MODE -> 0
            Theme.DARK_MODE -> 1
            else -> 0
        }
        setAppTheme(themePosition!!)
    }

    private fun setAppTheme(themePosition: Int) {
        userPrefs.updateTheme(
            when (themePosition) {
                0 -> Theme.LIGHT_MODE
                1 -> Theme.DARK_MODE
                else -> Theme.LIGHT_MODE
            }
        )
    }
}