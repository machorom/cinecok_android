package com.daou.cinecok.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.daou.cinecok.R
import com.daou.cinecok.ui.main.MainActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, MainActivity::class.java).run{
                        startActivity(this)
                    }
            finish()
        }, 1000)

    }
}