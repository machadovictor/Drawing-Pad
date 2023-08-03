package com.bignerdranch.android.androiddrawingpad

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val appNameTextView: TextView = findViewById(R.id.appNameTextView)
        val startButton: Button = findViewById(R.id.startButton)

        // Slide the app name across the screen
        val animator = ObjectAnimator.ofFloat(appNameTextView, "translationX", -2000f, 0f)
        animator.apply {
            duration = 2000
            interpolator = LinearInterpolator()
            start()
        }

        startButton.setOnClickListener {
            // Launch the main drawing activity
            startActivity(MainActivity.newIntent(this))
            finish()
        }
    }
}

