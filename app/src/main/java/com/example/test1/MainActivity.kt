package com.example.test1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

//        val TAG = "MainActivity"


        val btnLogin:Button = findViewById(R.id.btnLogin)
        val btnSignUp:Button = findViewById(R.id.btnSignUp)

        btnLogin.setOnClickListener {
            val i1 = Intent(this,Activity_Login::class.java)
            startActivity(i1)
        }
        btnSignUp.setOnClickListener {
            val i2 = Intent(this,Activity_SignUp::class.java)
            startActivity(i2)
        }


    }
}