package com.agmal.agmal.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.agmal.agmal.BuildConfig
import com.agmal.agmal.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        versi_apps.text = BuildConfig.VERSION_NAME

        versi_apps.apply {
            alpha = 0F
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(2000)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(p0: Animator?) {
                        iv_splashLogo_splash.apply {
                            alpha = 0f
                            visibility = View.VISIBLE
                            animate()
                                .alpha(1f)
                                .setDuration(2000)
                                .setListener(object : AnimatorListenerAdapter(){
                                    override fun onAnimationEnd(p0: Animator?) {
                                        startActivity(Intent(this@SplashScreen,MetaActivity::class.java))
                                        finish()
                                    }
                                })
                        }
                    }
                })
        }
    }
}