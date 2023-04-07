package com.example.test1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.test1.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class Activity_Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //login google
        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        // Initialize firebase user
        val firebaseUser: FirebaseUser? = firebaseAuth.currentUser

        // Initialize sign in options the client-id is copied form google-services.json file
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(this@Activity_Login, googleSignInOptions)
        binding.btnLoginGoogle.setOnClickListener { // Initialize sign in intent
            val intent: Intent = googleSignInClient.signInIntent
            // Start activity for result
            startActivityForResult(intent, 100)
        }
        if (firebaseUser != null) {
            // When user already sign in redirect to profile activity
            startActivity(
                Intent(
                    this,
                    ActivityHomeLoginGoogle::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }



        binding.textViewSignUp.setOnClickListener {
            val i = Intent(this@Activity_Login,Activity_SignUp::class.java)
            startActivity(i)
        }

        binding.btnLoginLogin.setOnClickListener {
            validation()
        }

        binding.btnLoginFacebook.setOnClickListener {
//            val intent = Intent(this@Activity_Login,FacebookLoginActivity::class.java)
            val intent = Intent(this@Activity_Login,FacebookLoginActivity::class.java)
            startActivity(intent)
        }

    }


    private fun validation() {
        val email = binding.loginEdtEmail.text.toString().trim()
        val password = binding.loginEdtPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()){
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    val i = Intent(this@Activity_Login,ActivityHomeLoginGoogle::class.java)
                    startActivity(i)
                }else{
                    Toast.makeText(this@Activity_Login,it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }else{
//            Toast.makeText(this@Activity_Login,"Không được để trống các ô nhập liệu ! \n Vui lòng nhập lại", Toast.LENGTH_SHORT).show()
            Toast.makeText(this@Activity_Login,"Fields not empty ! \n Please re-enter", Toast.LENGTH_SHORT).show()
        }


    }

    private fun displayToast(s: String) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check condition
        if (requestCode == 100) {
            // When request code is equal to 100 initialize task
            val signInAccountTask: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            // check condition
            if (signInAccountTask.isSuccessful) {
                // When google sign in successful initialize string
                val s = "Google sign in successful"
                // Display Toast
                displayToast(s)
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    val googleSignInAccount = signInAccountTask.getResult(ApiException::class.java)
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        val authCredential: AuthCredential = GoogleAuthProvider.getCredential(
                            googleSignInAccount.idToken, null
                        )
                        // Check credential
                        firebaseAuth.signInWithCredential(authCredential)
                            .addOnCompleteListener(this) { task ->
                                // Check condition
                                if (task.isSuccessful) {
                                    // When task is successful redirect to profile activity
                                    startActivity(
                                        Intent(
                                            this,
                                            ActivityHomeLoginGoogle::class.java
                                        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    )
                                    // Display Toast
                                    displayToast("Firebase authentication successful")
                                } else {
                                    // When task is unsuccessful display Toast
                                    displayToast(
                                        "Authentication Failed :" + task.exception?.message
                                    )
                                }
                            }
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }

            }
        }}}



//package com.example.test1
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
//import com.example.test1.databinding.ActivityLoginBinding
//import com.example.test1.databinding.ActivitySignUpBinding
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.tasks.Task
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.GoogleAuthProvider
//
//class Activity_Login : AppCompatActivity() {
//    private lateinit var binding: ActivityLoginBinding
//    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var googleSignInClient: GoogleSignInClient
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_login)
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        //firebase
//        firebaseAuth = FirebaseAuth.getInstance()
//
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this,gso)
//
//      binding.textViewSignUp.setOnClickListener {
//          val i = Intent(this@Activity_Login,Activity_SignUp::class.java)
//          startActivity(i)
//      }
//
//        binding.btnLoginLogin.setOnClickListener {
//
//        }
//
//        binding.btnLoginGoogle.setOnClickListener {
//            signInGoogle()
//        }
//
//
//    }
//
//    private fun signInGoogle() {
//        val signInIntent = googleSignInClient.signInIntent
//        launcher.launch(signInIntent)
//    }
//
//    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//        result ->
//            if (result.resultCode == Activity.RESULT_OK){
//                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//                handleResults(task)
//            }
//    }
//
//    private fun handleResults(task: Task<GoogleSignInAccount>) {
//        if (task.isSuccessful){
//            val account:GoogleSignInAccount? = task.result
//            if (account!=null){
//                updateUi(account)
//            }
//        }else{
//            Toast.makeText(this,task.exception.toString(),Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun updateUi(account: GoogleSignInAccount) {
//        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener{
//            if (it.isSuccessful){
//                val  intent  = Intent(this,HomeActivity::class.java)
//                intent.putExtra("email",account.email)
//                intent.putExtra("name",account.displayName)
//                startActivity(intent)
//            }else{
//                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun validation() {
//        val email = binding.loginEdtEmail.text.toString().trim()
//        val password = binding.loginEdtPassword.text.toString().trim()
//
//        if (email.isNotEmpty() && password.isNotEmpty()){
//                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
//                    if (it.isSuccessful){
//                        val i = Intent(this@Activity_Login,HomeActivity::class.java)
//                        startActivity(i)
//                    }else{
//                        Toast.makeText(this@Activity_Login,it.exception.toString(), Toast.LENGTH_SHORT).show()
//                    }
//                }
//        }else{
////            Toast.makeText(this@Activity_Login,"Không được để trống các ô nhập liệu ! \n Vui lòng nhập lại", Toast.LENGTH_SHORT).show()
//            Toast.makeText(this@Activity_Login,"Fields not empty ! \n Please re-enter", Toast.LENGTH_SHORT).show()
//        }
//
//    }
//}