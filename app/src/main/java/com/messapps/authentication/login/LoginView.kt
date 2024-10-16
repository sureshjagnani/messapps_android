package com.messapps.authentication.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.messapps.MyApp
import com.messapps.R
import com.messapps.authentication.otpAuth.OtpAuthView
import com.messapps.databinding.LoginViewBinding
import com.messapps.utils.CALL_FROM
import com.messapps.utils.Theme
import com.messapps.utils.UserPreferencesRepository

class LoginView : AppCompatActivity() {
    private var themePosition: Int? = null
    private var userPrefs: UserPreferencesRepository = MyApp.instance.userPreferences
    private lateinit var binding: LoginViewBinding
    private val signIn = 0
    private val signUp = 1
    private var setScreen = 0
    private var mSave: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.customLayout.llLight.setOnClickListener {
            setAppTheme(0)
        }
        binding.customLayout.llDark.setOnClickListener {
            setAppTheme(1)
        }

        binding.llSignIN.setOnClickListener {
            setScreen = signIn
            setUpView(signIn)
        }
        binding.llSignUp.setOnClickListener {
            setScreen = signUp
            setUpView(signUp)
        }
        binding.llNewOrRegister.setOnClickListener {
            if (setScreen == signIn) {
                setScreen = signUp
                setUpView(signUp)
            } else if (setScreen == signUp) {
                setScreen = signIn
                setUpView(signIn)
            }
        }
        binding.llSaveInfo.setOnClickListener {
            if (!mSave) {
                binding.tvbgSI.background =
                    AppCompatResources.getDrawable(
                        this@LoginView,
                        R.drawable.bg_save_login_selected
                    )
                mSave = true
            } else if (mSave) {
                binding.tvbgSI.background =
                    AppCompatResources.getDrawable(
                        this@LoginView,
                        R.drawable.bg_save_login
                    )
                mSave = false
            }
        }
        setUpView(signIn)
        binding.llLogin.setOnClickListener {
            callView()
        }
        binding.llRegister.setOnClickListener {
            CALL_FROM = 0
            callView()
        }
        binding.llForgot.setOnClickListener {
            CALL_FROM = 1
            callView()
        }
        initTheme()
    }

    private fun callView() {
        val i = Intent(this@LoginView, OtpAuthView::class.java)
        startActivity(i)
        finish()
    }

    private fun setUpView(position: Int) {
        if (position == signIn) {
            binding.llLogin.visibility = View.VISIBLE
            binding.llForgot.visibility = View.VISIBLE
            binding.llName.visibility = View.GONE
            binding.llRegister.visibility = View.GONE
            binding.llEmail.background =
                AppCompatResources.getDrawable(this@LoginView, R.drawable.bg_input_one)
            binding.lblNewUser.text = "New User?"
            binding.lblRegi.text = "Register"
            binding.llSignIN.background=
                AppCompatResources.getDrawable(this@LoginView, R.drawable.btn_sel)
            binding.llSignUp.background=
                AppCompatResources.getDrawable(this@LoginView, R.drawable.btn_unsel)
        } else if (position == signUp) {
            binding.llLogin.visibility = View.GONE
            binding.llForgot.visibility = View.GONE
            binding.llName.visibility = View.VISIBLE
            binding.llRegister.visibility = View.VISIBLE

            binding.llEmail.background =
                AppCompatResources.getDrawable(this@LoginView, R.drawable.bg_input_center)
            binding.lblNewUser.text = "Already Registered?"
            binding.lblRegi.text = "Sign in"
            binding.llSignUp.background=
                AppCompatResources.getDrawable(this@LoginView, R.drawable.btn_sel)
            binding.llSignIN.background=
                AppCompatResources.getDrawable(this@LoginView, R.drawable.btn_unsel)
        }
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