package com.example.test1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseAuth

class FacebookLoginActivity : AppCompatActivity() {
    var callbackManager = CallbackManager.Factory.create()
    var auth= FirebaseAuth.getInstance()
   lateinit var login_button :Button
    private val accessToken =AccessToken.getCurrentAccessToken()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_login)

        login_button = findViewById(R.id.login_buttonFB)

        if (accessToken!=null && !accessToken.isExpired){
            startActivity(Intent(this@FacebookLoginActivity,ActivityHomeFacebookLogin::class.java))
            finish()
        }

        LoginManager.getInstance().registerCallback(callbackManager,
        object :FacebookCallback<LoginResult>{
            override fun onCancel() {
                TODO("Not yet implemented")
            }

            override fun onError(error: FacebookException) {
                TODO("Not yet implemented")
            }

            override fun onSuccess(result: LoginResult) {
                startActivity(Intent(this@FacebookLoginActivity,ActivityHomeFacebookLogin::class.java))
                finish()
            }
        })

        login_button.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile","email"))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode,resultCode,data)

        super.onActivityResult(requestCode, resultCode, data)

    }
}