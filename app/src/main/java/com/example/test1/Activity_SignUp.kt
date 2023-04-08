package com.example.test1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.test1.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class Activity_SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_up)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebase
        firebaseAuth = FirebaseAuth.getInstance()

        //validation username & password
        binding.btnSignUpSignUp.setOnClickListener {
            ValidationInfor()
        }
        binding.textViewLogin.setOnClickListener {
            val i = Intent(this@Activity_SignUp,Activity_Login::class.java)
            startActivity(i)
        }
    }

    private fun ValidationInfor() {
        val email = binding.SignUpEdtEmail.text.toString().trim()
        val password = binding.SignUpEdtPassword.text.toString().trim()
        val re_password = binding.SignUpEdtRePassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty() && re_password.isNotEmpty()){
            if (password == re_password) {
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful){
                        val i = Intent(this@Activity_SignUp,ActivityHomeLoginGoogle::class.java)
//                        val i = Intent(this@Activity_SignUp,Activity_Login::class.java)
                        startActivity(i)
                    }else{
                        Toast.makeText(this@Activity_SignUp,it.exception.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
//                Toast.makeText(this@Activity_SignUp,"Mật khẩu và nhập lại mật khẩu không khớp ! \n Vui lòng nhập lại",Toast.LENGTH_SHORT).show()
                Toast.makeText(this@Activity_SignUp,"Password and re-password not match ! \n Please re-enter",Toast.LENGTH_SHORT).show()
            }
        }else{
//            Toast.makeText(this@Activity_SignUp,"Không được để trống các ô nhập liệu ! \n Vui lòng nhập lại",Toast.LENGTH_SHORT).show()
            Toast.makeText(this@Activity_SignUp,"Fields not empty ! \n Please re-enter",Toast.LENGTH_SHORT).show()
        }

    }
}
