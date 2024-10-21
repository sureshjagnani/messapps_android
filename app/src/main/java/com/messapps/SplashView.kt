package com.messapps

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
    private val splashTimeOut = 7500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.llLight.setOnClickListener {
            setAppTheme(0)
        }
        binding.llDark.setOnClickListener {
            setAppTheme(1)
        }
        Handler().postDelayed({
            val i: Intent = Intent(this@SplashView, LoginView::class.java)
            startActivity(i)
            finish()
        }, splashTimeOut.toLong())
        initTheme()

        binding.imgSplash.loadGif(R.drawable.splash)

    }

    private fun AppCompatImageView.loadGif(@DrawableRes resGif: Int) {
//        Glide.with(this)
//            .asGif()
//            .load(resGif)
//            .into(this)

        Glide.with(this).asGif().load(resGif).addListener(object : RequestListener<GifDrawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<GifDrawable>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: GifDrawable,
                model: Any,
                target: Target<GifDrawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                binding.imgIcon.visibility = View.GONE
                this@loadGif.setImageDrawable(resource)
                resource.start()
                return true
            }
        }).into(this)

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