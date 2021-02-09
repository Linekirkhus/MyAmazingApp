package com.example.myamazingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth



class MainActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()


        val btnToRegister= findViewById<Button>(R.id.to_register_btn)
        btnToRegister?.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        val btnToLogin= findViewById<Button>(R.id.to_login_btn)
        btnToLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}