package com.example.mylibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class SignUp : AppCompatActivity() {

    lateinit var  dbSignUp: DatabaseReference
    lateinit var  mAuth : FirebaseAuth
    lateinit var  btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        dbSignUp = FirebaseDatabase.getInstance().reference
        btnLogin = findViewById(R.id.btnSignInOnSignUp)

        btnSignUp.setOnClickListener {
            val name = etNamaSignUp.text.toString()
            val email = etEmailSignUp.text.toString()
            val password = etPassword.text.toString()

            if (name !="" && email != "" && password != ""){
                signUp(email,password)
            } else {
                Toast.makeText(this, "Masih ada field yang kosong",Toast.LENGTH_SHORT).show()
        }
        }

        btnLogin.setOnClickListener {
            startActivity(Intent(this,SignIn::class.java))
        }

    }

    private fun signUp(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    val name = etNamaSignUp.text.toString()
                    val email = etEmailSignUp.text.toString()
                    val uuid = mAuth.uid.toString()

                    dbSignUp.child("User").child((uuid))
                        .setValue(UserClass(name,email,uuid))
                    Toast.makeText(this,"Sign Up Berhasil", Toast.LENGTH_SHORT).show()
                    Log.i("SignUp","Sign Up Berhasil")
                    etEmailSignUp.text.clear()
                    etNamaSignUp.text.clear()
                    etPassword.text.clear()
                } else {
                    Toast.makeText(this,"Sign Up Gagal",Toast.LENGTH_SHORT).show()
                    Log.e("SignUp","Sign Up Gagal")
                }
            }
    }
}