package com.example.myamazingapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        auth = FirebaseAuth.getInstance()

        val registerBtn = findViewById<Button>(R.id.register_btn)
        registerBtn.setOnClickListener {
            signUpUser()
        }

        val loginLink = findViewById<TextView>(R.id.login_account)
        loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun signUpUser() {
        val tv_fullname = findViewById<TextView>(R.id.person_name)
        val tv_username = findViewById<TextView>(R.id.email)
        val tv_password = findViewById<TextView>(R.id.password)
        //val tv_phone = findViewById<TextView>(R.id.phone)
        
        if (tv_fullname.text.toString().isEmpty()) {
            tv_fullname.error = "Please Name"
            tv_fullname.requestFocus()
            return
        }

        if (tv_username.text.toString().isEmpty()) {
            tv_username.error = "Please enter Email"
            tv_username.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(tv_username.text.toString()).matches()) {
            tv_username.error = "Please enter valid email"
            tv_username.requestFocus()
            return
        }


        if (tv_password.text.toString().isEmpty()) {
            tv_password.error = "Please enter password"
            tv_password.requestFocus()
            return
        }
/*
        if (tv_phone.text.toString().isEmpty()) {
            tv_phone.error = "Please enter Email"
            tv_phone.requestFocus()
            return
        }
 */

        auth.createUserWithEmailAndPassword(
            tv_username.text.toString(),
            tv_password.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                        }
             } else {
                 Toast.makeText(
                     baseContext, "Registration failed. Please try again",
                     Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}