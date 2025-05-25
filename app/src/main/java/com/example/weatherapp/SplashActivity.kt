package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize the ProgressBar
        progressBar = findViewById(R.id.progressBar)

        // Show the ProgressBar while loading
        progressBar.visibility = View.VISIBLE

        // Use a coroutine to handle the delay
        GlobalScope.launch(Dispatchers.Main) {
            delay(3000) // Simulate loading for 3 seconds
            progressBar.visibility = View.GONE // Hide the ProgressBar after loading
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}