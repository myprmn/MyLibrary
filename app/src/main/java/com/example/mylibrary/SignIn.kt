package com.example.mylibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var  dbSignIn : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        dbSignIn = FirebaseDatabase.getInstance().reference

        btnSignIn.setOnClickListener {
            val email = etEmailSignIn.text.toString()
            val password = etPasswordSignIn.text.toString()
            if (email !="" && password!=""){
                DataLogin(email,password)
            } else {
                Toast.makeText(this,"Field Masih Kosong",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun DataLogin(email:String, password:String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                Toast.makeText(this,"Authentification Success.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,DataBuku::class.java))
                    Log.i("Login","Login Berhasil")
            } else {
                Toast.makeText(this,"Authentification Failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}