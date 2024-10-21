package com.messapps.authentication.resetAuth

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.messapps.MainActivity
import com.messapps.MyApp
import com.messapps.R
import com.messapps.authentication.login.LoginView

import com.messapps.data.repository.authenticat.OtpMapper
import com.messapps.data.repository.authenticat.OtpResp
import com.messapps.data.service.RetrofitClass
import com.messapps.data.vmodelData.ApiError
import com.messapps.data.vmodelData.authentication.ResetPasswordOtp
import com.messapps.databinding.ResetViewBinding

import com.messapps.utils.INTERNET_NOT_AVAILABLE
import com.messapps.utils.OTP_MSG
import com.messapps.utils.PASSWORD_FAIL_MSG
import com.messapps.utils.PreferencesUtils
import com.messapps.utils.UserPreferencesRepository
import com.messapps.utils.Theme
import com.messapps.utils.hideProgressBar
import com.messapps.utils.isNetworkAvailable
import com.messapps.utils.isValidPassword
import com.messapps.utils.mRToken
import com.messapps.utils.progressDialogMsg
import com.messapps.utils.showProgressBar

class ResetAuthView : AppCompatActivity(), OtpResp {
    private var themePosition: Int? = null
    private var userThemePrefs: UserPreferencesRepository = MyApp.instance.userPreferences
    private lateinit var binding: ResetViewBinding
    private var mSave: Boolean = false
    lateinit var mProgressDialog: ProgressDialog
    private var userPrefs: PreferencesUtils = MyApp.instance.preferencesUtils
    private lateinit var mOptMapper: OtpMapper
    private var mCheck:Boolean = true
    private var mCheckRe:Boolean = true
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ResetViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mOptMapper = OtpMapper(this, RetrofitClass.getInstance(this@ResetAuthView)!!)
        mProgressDialog = showProgressBar(progressDialogMsg, this)
        binding.etPassword.addTextChangedListener(mTextWatcher)
        binding.etRePassword.addTextChangedListener(mTextWatcher)
        binding.llContinue.setOnClickListener {
            callOtpVerify(0)
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
        /*binding.ivEye.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT)
                MotionEvent.ACTION_UP -> binding.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }

            v?.onTouchEvent(event) ?: true
        }
        binding.ivEyeRe.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> binding.etRePassword.setInputType(InputType.TYPE_CLASS_TEXT)
                MotionEvent.ACTION_UP -> binding.etRePassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }

            v?.onTouchEvent(event) ?: true
        }*/
        binding.ivEye.setOnClickListener(View.OnClickListener {
            if (mCheck) {
                binding.etPassword.transformationMethod = SingleLineTransformationMethod()
                mCheck = false
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod()
                mCheck = true
            }
            binding.etPassword.setSelection(binding.etPassword.getText()!!.length)
        })
        binding.ivEyeRe.setOnClickListener(View.OnClickListener {
            if (mCheckRe) {
                binding.etPassword.transformationMethod = SingleLineTransformationMethod()
                mCheckRe = false
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod()
                mCheckRe = true
            }
            binding.etPassword.setSelection(binding.etPassword.getText()!!.length)
        })

        initTheme()
    }

    private fun initTheme() {
        themePosition = when (userThemePrefs.appTheme) {
            Theme.LIGHT_MODE -> 0
            Theme.DARK_MODE -> 1
            else -> 0
        }
        setAppTheme(themePosition!!)
    }

    private fun setAppTheme(themePosition: Int) {
        userThemePrefs.updateTheme(
            when (themePosition) {
                0 -> Theme.LIGHT_MODE
                1 -> Theme.DARK_MODE
                else -> Theme.LIGHT_MODE
            }
        )
    }
    private val mTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
        }

        override fun afterTextChanged(editable: Editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues()
        }
    }

    fun checkFieldsForEmptyValues() {
        val s1: String = binding.etPassword.getText().toString().trim { it <= ' ' }
        val s2: String = binding.etRePassword.getText().toString().trim { it <= ' ' }

            if (s1 == "" || s2 == "") {
                binding.llContinue.background =
                    AppCompatResources.getDrawable(this@ResetAuthView, R.drawable.bg_login)
                binding.tvLbl.setTextColor(getResources().getColor(R.color.black))
            } else {
                binding.llContinue.background =
                    AppCompatResources.getDrawable(this@ResetAuthView, R.drawable.bg_login_active)
                binding.tvLbl.setTextColor(getResources().getColor(R.color.white))
            }
    }
    private fun callOtpVerify(mCallFrom: Int) {
        if (!TextUtils.isEmpty(
                binding.etPassword.getText().toString().trim { it <= ' ' }) && !TextUtils.isEmpty(
                binding.etRePassword.getText().toString().trim { it <= ' ' })
        ) {
            if (isValidPassword(
                    binding.etPassword.getText().toString().trim { it <= ' ' }) && isValidPassword(
                    binding.etRePassword.getText().toString().trim { it <= ' ' })
            ) {
                if (binding.etPassword.getText().toString() == binding.etRePassword.getText().toString()
                ) {
                    val data = HashMap<String, String>()
                    data["newPassword"] = binding.etPassword.getText().toString()
                    data["recoveryToken"] = mRToken

                    if (isNetworkAvailable(this@ResetAuthView)) {
                        mProgressDialog.show()
                        mOptMapper.recoverPassword(
                            RetrofitClass.getHeaders(this, false), data, mCallFrom
                        )

                    } else {
                        Toast.makeText(this, INTERNET_NOT_AVAILABLE, Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Password Not matching", Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(this, PASSWORD_FAIL_MSG, Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(this, OTP_MSG, Toast.LENGTH_LONG).show()
        }
    }

    override fun onSuc(response: ResetPasswordOtp?, mFrom: Int) {
        hideProgressBar(mProgressDialog)
        if (response != null) {
            if (response.ok!!) {
                if (response.isForceLogout!!) {
                    val i = Intent(this, LoginView::class.java)
                    i.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(i)
                    finish()
                } else {
                    val i = Intent(this, MainActivity::class.java)
                    i.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(i)
                    finish()
                }
            }

        }

    }

    override fun onFail(data: ApiError?) {
        hideProgressBar(mProgressDialog)
        Toast.makeText(this, data!!.message!!, Toast.LENGTH_LONG).show()
    }

    override fun onFailRes(msg: String?) {
        hideProgressBar(mProgressDialog)
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}