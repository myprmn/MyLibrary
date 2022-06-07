package com.example.mylibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

//    lateinit var ivLogo : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        ivLogo = findViewById(R.id.ivLogo)
        ivLogo.alpha = 0f
        ivLogo.animate().setDuration(1500).alpha(1f).withEndAction {
            val intent = Intent (this,SignUp::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}