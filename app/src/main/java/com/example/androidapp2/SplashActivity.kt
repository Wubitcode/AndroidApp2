package com.example.androidapp2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

/**
 * Displays the application splash screen when the app launches.
 *
 * The splash screen is shown briefly before automatically
 * navigating the user to the main coffee menu.
 */
class SplashActivity : AppCompatActivity() {

    /**
     * Initializes the splash screen and schedules navigation
     * to MainActivity after a short delay.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect this activity to its splash screen layout.
        setContentView(R.layout.activity_splash)

        /*
         * Display the splash screen briefly before opening
         * the main coffee menu.
         */
        Handler(Looper.getMainLooper()).postDelayed({

            // Create navigation from the splash screen to MainActivity.
            val mainIntent =
                Intent(this, MainActivity::class.java)

            startActivity(mainIntent)

            /*
             * Remove SplashActivity from the activity stack so
             * pressing Back does not return to the splash screen.
             */
            finish()

        }, SPLASH_DELAY)
    }

    companion object {

        /**
         * Duration, in milliseconds, that the splash screen
         * remains visible before the main menu opens.
         */
        private const val SPLASH_DELAY = 1500L
    }
}