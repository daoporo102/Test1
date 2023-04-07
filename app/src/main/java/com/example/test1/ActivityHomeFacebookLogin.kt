package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class ActivityHomeFacebookLogin : AppCompatActivity() {

   private lateinit var txtname:TextView
    private lateinit var txtemail:TextView
    private  lateinit var image:ImageView
    private  lateinit var btnLogOut:Button
    var auth= FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_facebook_login)

        txtname = findViewById(R.id.txtName)
        txtemail = findViewById(R.id.txtEmail)
        btnLogOut = findViewById(R.id.btnLogOut)
        image = findViewById(R.id.avtFB)

        val accessToken = AccessToken.getCurrentAccessToken()

        val request = GraphRequest.newMeRequest(
            accessToken) { jsonObject, response -> // Getting FB User Data
             val email = jsonObject?.getString("email")
            val name = jsonObject?.getString("name")
            val profileUrl = jsonObject?.getJSONObject("picture")
                ?.getJSONObject("data")?.getString("url")

            txtname.text = name
            txtemail.text = email
//            Glide.with(applicationContext).
            Picasso.get().load(profileUrl).into(image)

        }

        val parameters = Bundle()
        parameters.putString("fields","id,name,link,picture.type(large),email")
        request.parameters = parameters
        request.executeAsync()

        btnLogOut.setOnClickListener {
            LoginManager.getInstance().logOut()
            startActivity(Intent(this@ActivityHomeFacebookLogin,Activity_Login::class.java))
            finish()
        }

    }
}
