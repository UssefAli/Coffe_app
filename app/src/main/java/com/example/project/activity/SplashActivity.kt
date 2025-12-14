package com.example.project.activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.project.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener{
//            startActivity(Intent(this , MainActivity::class.java))
            val flutterIntent = Intent(Intent.ACTION_VIEW, Uri.parse("coffeeauth://login"))

            try {
                startActivity(flutterIntent)
                // Optional: finish() // Uncomment if you want to close the Splash screen so the user can't go back to it
            } catch (e: ActivityNotFoundException) {
//                Toast.makeText(this, "Please install the Auth App first!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

    }
}