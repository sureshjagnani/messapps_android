package com.messapps.authentication.login

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
import com.messapps.authentication.otpAuth.OtpAuthView
import com.messapps.data.repository.authenticat.LoginMapper
import com.messapps.data.repository.authenticat.LoginResp
import com.messapps.data.service.RetrofitClass
import com.messapps.data.vmodelData.ApiError
import com.messapps.data.vmodelData.authentication.LoginResponse
import com.messapps.databinding.LoginViewBinding
import com.messapps.utils.CALL_FROM
import com.messapps.utils.EMAIL_FAIL_MSG
import com.messapps.utils.EMAIL_MSG
import com.messapps.utils.INTERNET_NOT_AVAILABLE
import com.messapps.utils.INVALID_CREDENTIALS_MSG
import com.messapps.utils.PASSWORD_FAIL_MSG
import com.messapps.utils.PreferencesUtils
import com.messapps.utils.Theme
import com.messapps.utils.UserPreferencesRepository
import com.messapps.utils.hideProgressBar
import com.messapps.utils.isNetworkAvailable
import com.messapps.utils.isValidEmail
import com.messapps.utils.isValidPassword
import com.messapps.utils.mEmail
import com.messapps.utils.mLogin
import com.messapps.utils.progressDialogMsg
import com.messapps.utils.showProgressBar


class LoginView : AppCompatActivity(), LoginResp {
    private var themePosition: Int? = null
    private var userThemePrefs: UserPreferencesRepository = MyApp.instance.userPreferences
    private lateinit var binding: LoginViewBinding
    private lateinit var mLoginMapper: LoginMapper
    private val signIn = 0
    private val signUp = 1
    private val mForgot = 2
    private var setScreen = 0
    private var mSave: Boolean = false
    lateinit var mProgressDialog: ProgressDialog
    private var userPrefs: PreferencesUtils = MyApp.instance.preferencesUtils
    private var mCheck: Boolean = true
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mLoginMapper = LoginMapper(this, RetrofitClass.getInstance(this@LoginView)!!)
        mProgressDialog = showProgressBar(progressDialogMsg, this)

        binding.etEmail.addTextChangedListener(mTextWatcher)
        binding.etPassword.addTextChangedListener(mTextWatcher)
        binding.etName.addTextChangedListener(mTextWatcher)

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

        setUpView(signIn)
        binding.llLogin.setOnClickListener {

            if (!TextUtils.isEmpty(
                    binding.etEmail.getText().toString().trim { it <= ' ' }) && !TextUtils.isEmpty(
                    binding.etPassword.getText().toString().trim { it <= ' ' })
            ) {
                //BaseConstant.isValidEmail(mEmail.getText().toString().trim()) ||
                if (isValidEmail(binding.etEmail.getText().toString().trim { it <= ' ' })) {
                    val data = HashMap<String, String>()
                    data["email"] = binding.etEmail.getText().toString().trim { it <= ' ' }
                    data["password"] = binding.etPassword.getText().toString().trim { it <= ' ' }
                    data["fcmToken"] = "FCMkey"
                    if (isNetworkAvailable(this@LoginView)) {
                        mProgressDialog.show()
                        mLoginMapper.loginEvent(RetrofitClass.getHeaders(this, false), data, signIn)
                    } else {
                        Toast.makeText(this, INTERNET_NOT_AVAILABLE, Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, EMAIL_FAIL_MSG, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, INVALID_CREDENTIALS_MSG, Toast.LENGTH_LONG).show()
            }
        }
        binding.llRegister.setOnClickListener {
            callRegister()
        }
        binding.llForgot.setOnClickListener {
            CALL_FROM = mForgot
            callForgot()
        }
        initTheme()
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
        val s1: String = binding.etEmail.getText().toString().trim { it <= ' ' }
        val s2: String = binding.etPassword.getText().toString().trim { it <= ' ' }
        val s3: String = binding.etName.getText().toString().trim { it <= ' ' }
        if (setScreen == signIn) {
            if (s1 == "" || s2 == "") {
                binding.llLogin.background =
                    AppCompatResources.getDrawable(this@LoginView, R.drawable.bg_login)
                binding.tvLbl.setTextColor(getResources().getColor(R.color.black))
            } else {
                binding.llLogin.background =
                    AppCompatResources.getDrawable(this@LoginView, R.drawable.bg_login_active)
                binding.tvLbl.setTextColor(getResources().getColor(R.color.white))
            }
        }
        if (setScreen == signUp) {
            if (s1 == "" || s2 == "" || s3 == "") {

                binding.llRegister.background =
                    AppCompatResources.getDrawable(this@LoginView, R.drawable.bg_login)
                binding.tvRegiLbl.setTextColor(getResources().getColor(R.color.black))

            } else {
                binding.llRegister.background =
                    AppCompatResources.getDrawable(this@LoginView, R.drawable.bg_login_active)
                binding.tvRegiLbl.setTextColor(getResources().getColor(R.color.white))
            }
        }
    }

    private fun callView() {
        val i = Intent(this@LoginView, OtpAuthView::class.java)
        startActivity(i)
        finish()
    }

    private fun callRegister() {
        if (!TextUtils.isEmpty(
                binding.etEmail.getText().toString().trim { it <= ' ' }) && !TextUtils.isEmpty(
                binding.etPassword.getText().toString().trim { it <= ' ' }) && !TextUtils.isEmpty(
                binding.etName.getText().toString().trim { it <= ' ' })
        ) {
            //BaseConstant.isValidEmail(mEmail.getText().toString().trim()) ||
            if (isValidEmail(binding.etEmail.getText().toString().trim { it <= ' ' })) {
                if (isValidPassword(binding.etPassword.getText().toString().trim { it <= ' ' })) {

                    val data = HashMap<String, String>()
                    data["email"] = binding.etEmail.getText().toString().trim { it <= ' ' }
                    data["password"] = binding.etPassword.getText().toString().trim { it <= ' ' }
                    data["name"] = binding.etName.getText().toString()
                    if (isNetworkAvailable(this@LoginView)) {
                        mProgressDialog.show()
                        mLoginMapper.signUpEvent(
                            RetrofitClass.getHeaders(this, false),
                            data,
                            signUp
                        )
                    } else {
                        Toast.makeText(this, INTERNET_NOT_AVAILABLE, Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, PASSWORD_FAIL_MSG, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, EMAIL_FAIL_MSG, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, INVALID_CREDENTIALS_MSG, Toast.LENGTH_LONG).show()
        }
    }

    private fun callForgot() {
        if (!TextUtils.isEmpty(binding.etEmail.getText().toString().trim { it <= ' ' })) {
            //BaseConstant.isValidEmail(mEmail.getText().toString().trim()) ||
            if (isValidEmail(binding.etEmail.getText().toString().trim { it <= ' ' })) {
                val data = HashMap<String, String>()
                data["email"] = binding.etEmail.getText().toString().trim { it <= ' ' }
                mEmail = binding.etEmail.getText().toString().trim { it <= ' ' }
                //data["password"] = binding.etPassword.getText().toString().trim { it <= ' ' }
                //data["name"] = binding.etName.getText().toString()
                if (isNetworkAvailable(this@LoginView)) {
                    mProgressDialog.show()
                    mLoginMapper.forgotPasswordEvent(
                        RetrofitClass.getHeaders(this, false),
                        data,
                        mForgot
                    )
                } else {
                    Toast.makeText(this, INTERNET_NOT_AVAILABLE, Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(this, EMAIL_FAIL_MSG, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, EMAIL_MSG, Toast.LENGTH_LONG).show()
        }
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
            binding.llSignIN.background =
                AppCompatResources.getDrawable(this@LoginView, R.drawable.btn_sel)
            binding.llSignUp.background =
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
            binding.llSignUp.background =
                AppCompatResources.getDrawable(this@LoginView, R.drawable.btn_sel)
            binding.llSignIN.background =
                AppCompatResources.getDrawable(this@LoginView, R.drawable.btn_unsel)
        }
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

    override fun onSuc(response: LoginResponse?, mFrom: Int) {
        hideProgressBar(mProgressDialog)
        if (response != null) {
            if (mFrom == signIn || mFrom == signUp) {

                userPrefs.setAccessToken(response.accessToken!!)
                userPrefs.setSessionToken(response.sessionUser!!.sessionToken!!)
                userPrefs.setUserInfo(response.sessionUser!!)
                if (mFrom == signIn) {
                    userPrefs.setLogin(mLogin)
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }
                if (mFrom == signUp) {
                    CALL_FROM = signUp
                    callView()
                }
            }
            if (mFrom == mForgot) {
                if (response.isSent!!) {
                    CALL_FROM = mForgot
                    callView()
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