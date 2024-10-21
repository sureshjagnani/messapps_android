package com.messapps.authentication.otpAuth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.messapps.MainActivity
import com.messapps.MyApp
import com.messapps.R
import com.messapps.authentication.resetAuth.ResetAuthView
import com.messapps.data.repository.authenticat.OtpMapper
import com.messapps.data.repository.authenticat.OtpResp
import com.messapps.data.service.RetrofitClass
import com.messapps.data.vmodelData.ApiError
import com.messapps.data.vmodelData.authentication.ResetPasswordOtp
import com.messapps.databinding.OtpViewBinding
import com.messapps.utils.CALL_FROM
import com.messapps.utils.INTERNET_NOT_AVAILABLE
import com.messapps.utils.OTP_MSG
import com.messapps.utils.PreferencesUtils
import com.messapps.utils.Theme
import com.messapps.utils.UserPreferencesRepository
import com.messapps.utils.hideProgressBar
import com.messapps.utils.isNetworkAvailable
import com.messapps.utils.mEmail
import com.messapps.utils.mLogin
import com.messapps.utils.mRToken
import com.messapps.utils.progressDialogMsg
import com.messapps.utils.showProgressBar


class OtpAuthView : AppCompatActivity(), OtpResp {
    private var themePosition: Int? = null
    private var userThemePrefs: UserPreferencesRepository = MyApp.instance.userPreferences
    private lateinit var binding: OtpViewBinding
    lateinit var mProgressDialog: ProgressDialog
    private var userPrefs: PreferencesUtils = MyApp.instance.preferencesUtils
    private lateinit var mOptMapper: OtpMapper
    var sb: StringBuilder = StringBuilder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OtpViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mOptMapper = OtpMapper(this, RetrofitClass.getInstance(this@OtpAuthView)!!)
        mProgressDialog = showProgressBar(progressDialogMsg, this)
        binding.llContinue.setOnClickListener {
            callOtpVerify(CALL_FROM)
        }

        binding.customLayout.llLight.setOnClickListener {
            setAppTheme(0)
        }
        binding.customLayout.llDark.setOnClickListener {
            setAppTheme(1)
        }

        binding.one.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
                //if ((sb.isEmpty()) and (binding.one.length() == 1))
                if (binding.one.length() == 1) {
                    // sb.append(s)
                    binding.one.clearFocus()
                    binding.two.requestFocus()
                    binding.two.setCursorVisible(true)
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
                //if (sb.length == 1) {
                //    sb.deleteCharAt(0)
                //}
            }

            override fun afterTextChanged(s: Editable) {
                //if (sb.isEmpty()) {
                //    binding.one.requestFocus()
                //}
                checkFieldsForEmptyValues()
            }
        })
        binding.two.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
                //if ((sb.isEmpty()) and (binding.two.length() == 1))
                if (binding.two.length() == 1) {
                    //sb.append(s)
                    binding.two.clearFocus()
                    binding.three.requestFocus()
                    binding.three.setCursorVisible(true)
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
                //if (sb.length == 1) {
                //    sb.deleteCharAt(0)
                //}
            }

            override fun afterTextChanged(s: Editable) {
                //if (sb.isEmpty()) {
                //    binding.one.requestFocus()
                //}
                checkFieldsForEmptyValues()
            }
        })
        binding.three.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
                //if ((sb.isEmpty()) and (binding.two.length() == 1))
                if (binding.three.length() == 1) {
                    //sb.append(s)
                    binding.three.clearFocus()
                    binding.four.requestFocus()
                    binding.four.setCursorVisible(true)
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
                //if (sb.length == 1) {
                //    sb.deleteCharAt(0)
                //}
            }

            override fun afterTextChanged(s: Editable) {
                //if (sb.isEmpty()) {
                //    binding.one.requestFocus()
                //}
                checkFieldsForEmptyValues()
            }
        })
        binding.four.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
                //if ((sb.isEmpty()) and (binding.two.length() == 1))
                if (binding.four.length() == 1) {
                    binding.four.clearFocus()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
                //if (sb.length == 1) {
                //    sb.deleteCharAt(0)
                //}
            }

            override fun afterTextChanged(s: Editable) {
                //if (sb.isEmpty()) {
                //    binding.one.requestFocus()
                //}
                checkFieldsForEmptyValues()
            }
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
    fun checkFieldsForEmptyValues() {

        if (!TextUtils.isEmpty(
                binding.one.getText().toString().trim { it <= ' ' }) && !TextUtils.isEmpty(
                binding.two.getText().toString().trim { it <= ' ' }) && !TextUtils.isEmpty(
                binding.three.getText().toString().trim { it <= ' ' }) && !TextUtils.isEmpty(
                binding.four.getText().toString().trim { it <= ' ' })
        ) {
            binding.llContinue.background =
                AppCompatResources.getDrawable(this@OtpAuthView, R.drawable.bg_login_active)
            binding.tvLbl.setTextColor(getResources().getColor(R.color.white))
        } else {
            binding.llContinue.background =
                AppCompatResources.getDrawable(this@OtpAuthView, R.drawable.bg_login)
            binding.tvLbl.setTextColor(getResources().getColor(R.color.black))
        }
    }
    private fun callOtpVerify(mCallFrom: Int) {
        if (!TextUtils.isEmpty(
                binding.one.getText().toString().trim { it <= ' ' }) && !TextUtils.isEmpty(
                binding.two.getText().toString().trim { it <= ' ' }) && !TextUtils.isEmpty(
                binding.three.getText().toString().trim { it <= ' ' }) && !TextUtils.isEmpty(
                binding.four.getText().toString().trim { it <= ' ' })
        ) {

            val data = HashMap<String, String>()
            if (mCallFrom == 2) {
                data["email"] = mEmail
            }
            if (mCallFrom == 1) {
                data["otpType"] = "CURRENT_EMAIL"
            }
            val code = buildString {
                append(binding.one.getText().toString())
                append(binding.two.getText().toString())
                append(binding.three.getText().toString())
                append(binding.four.getText().toString())
            }
            data["code"] = code
            if (isNetworkAvailable(this@OtpAuthView)) {
                mProgressDialog.show()

                if (mCallFrom == 2) {
                    mOptMapper.otpVerify(
                        RetrofitClass.getHeaders(this, false), data,
                        mCallFrom
                    )
                }
                if (mCallFrom == 1) {
                    mOptMapper.verifyOtpCode(
                        RetrofitClass.getHeaders(this, false), data, mCallFrom
                    )
                }
            } else {
                Toast.makeText(this, INTERNET_NOT_AVAILABLE, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, OTP_MSG, Toast.LENGTH_LONG).show()
        }
    }

    override fun onSuc(response: ResetPasswordOtp?, mFrom: Int) {
        hideProgressBar(mProgressDialog)
        if (response != null) {
            if (mFrom == 1) {
                //signup
                userPrefs.setSessionToken(response!!.sessionUser!!.sessionToken!!)
                userPrefs.setUserInfo(response.sessionUser!!)
                userPrefs.setLogin(mLogin)
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }
            if (mFrom == 2) {
                //forgot
                mRToken = response.recoveryToken!!
                val i = Intent(this@OtpAuthView, ResetAuthView::class.java)
                startActivity(i)
                finish()
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