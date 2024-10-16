package com.messapps.authentication.otpAuth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.messapps.MainActivity
import com.messapps.MyApp
import com.messapps.authentication.resetAuth.ResetAuthView
import com.messapps.databinding.OtpViewBinding
import com.messapps.utils.CALL_FROM
import com.messapps.utils.UserPreferencesRepository
import com.messapps.utils.Theme

class OtpAuthView : AppCompatActivity() {
    private var themePosition: Int? = null
    private var userPrefs: UserPreferencesRepository = MyApp.instance.userPreferences
    private lateinit var binding: OtpViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OtpViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.llContinue.setOnClickListener {
            if (CALL_FROM == 0) {
                val i = Intent(this@OtpAuthView, MainActivity::class.java)
                startActivity(i)
                finish()
            } else if (CALL_FROM == 1) {
                val i = Intent(this@OtpAuthView, ResetAuthView::class.java)
                startActivity(i)
                finish()
            }
        }

        binding.customLayout.llLight.setOnClickListener {
            setAppTheme(0)
        }
        binding.customLayout.llDark.setOnClickListener {
            setAppTheme(1)
        }
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