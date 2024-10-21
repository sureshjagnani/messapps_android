package com.messapps.data.service

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.google.gson.GsonBuilder
import com.messapps.MyApp
import com.messapps.utils.COMMON_URL
import com.messapps.utils.PreferencesUtils
import com.messapps.utils.UserPreferencesRepository
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitClass {
    private var retrofitInterface: RetrofitInterface? = null
    private val commonLoginRetrofitInterface: RetrofitInterface? = null
    private var userPrefs: PreferencesUtils = MyApp.instance.preferencesUtils
    fun getInstance(context: Context?): RetrofitInterface? {
        if (retrofitInterface == null) {
            //            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            val okHttpClient1 = getUnsafeOkHttpClientUsingProvider(context)

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(COMMON_URL) //https://192.168.0.112/  Constants.BASE_URL
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient1)
                .build()

            if (retrofit != null) {
                retrofitInterface = retrofit.create(
                    RetrofitInterface::class.java
                )
            }
        }
        return retrofitInterface
    }

    fun clearInstance() {
        if (retrofitInterface != null) {
            retrofitInterface = null
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun getHeaders(context: Activity?, mToken: Boolean): HashMap<String, String> {
        val headers = HashMap<String, String>()
        headers["Content-Type"] = "application/json"
        headers["Accept"] = "*/*"
        headers["X-API-Key"] = "messapps2024"
        if (mToken)
            headers["X-Session-Key"] = userPrefs.getSessionToken()!!
        return headers
    }

    private fun getUnsafeOkHttpClientUsingProvider(context: Context?): OkHttpClient {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
            )


            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder: Builder = Builder()
            builder.connectTimeout(30, TimeUnit.SECONDS)
            builder.writeTimeout(30, TimeUnit.SECONDS)
            builder.readTimeout(30, TimeUnit.SECONDS)


            // -----           UNCOMMENT BELOW LINES BEFORE CHECKIN..DO NOT COMMIT THIS
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(interceptor)
            //----------
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { hostname: String?, session: SSLSession? -> true })

            return builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
