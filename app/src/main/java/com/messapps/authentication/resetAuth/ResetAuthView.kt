package com.messapps.authentication.resetAuth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.messapps.MainActivity
import com.messapps.MyApp
import com.messapps.R
import com.messapps.databinding.OtpViewBinding
import com.messapps.databinding.ResetViewBinding
import com.messapps.utils.CALL_FROM
import com.messapps.utils.UserPreferencesRepository
import com.messapps.utils.Theme

class ResetAuthView : AppCompatActivity() {
    private var themePosition: Int? = null
    private var userPrefs: UserPreferencesRepository = MyApp.instance.userPreferences
    private lateinit var binding: ResetViewBinding
    private var mSave: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ResetViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.llContinue.setOnClickListener {

            val i = Intent(this@ResetAuthView, MainActivity::class.java)
            startActivity(i)
            finish()

        }

        binding.customLayout.llLight.setOnClickListener {
            setAppTheme(0)
        }
        binding.customLayout.llDark.setOnClickListener {
            setAppTheme(1)
        }
        binding.llSaveInfo.setOnClickListener {
            if (!mSave) {
                binding.tvbgSI.background =
                    AppCompatResources.getDrawable(
                        this@ResetAuthView,
                        R.drawable.bg_save_login_selected
                    )
                mSave = true
            } else if (mSave) {
                binding.tvbgSI.background =
                    AppCompatResources.getDrawable(
                        this@ResetAuthView,
                        R.drawable.bg_save_login
                    )
                mSave = false
            }
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