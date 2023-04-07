package com.example.test1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.test1.databinding.ActivityHomeLoginGoogleBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class ActivityHomeLoginGoogle : AppCompatActivity() {
    private lateinit var binding: ActivityHomeLoginGoogleBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
        binding = ActivityHomeLoginGoogleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebase
        firebaseAuth = FirebaseAuth.getInstance()
        // Initialize firebase user
        val firebaseUser = firebaseAuth.currentUser
        // Check condition
        if (firebaseUser != null) {
            // set name on text view
           binding.textViewHome.text = firebaseUser.displayName
        }

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
        binding.btnLogOut.setOnClickListener {
            // Sign out from google
            googleSignInClient.signOut().addOnCompleteListener { task ->
                // Check condition
                if (task.isSuccessful) {
                    // When task is successful sign out from firebase
                    firebaseAuth.signOut()
                    // Display Toast
                    Toast.makeText(applicationContext, "Logout successful", Toast.LENGTH_SHORT).show()
                    // Finish activity
                    finish()
                }
            }
        }
    }
}

