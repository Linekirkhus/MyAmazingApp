package com.example.myamazingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userName : TextView

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        auth = FirebaseAuth.getInstance()

       val user = auth.currentUser

       val updateProfileBtn = findViewById<Button>(R.id.btn_update_profile)
       updateProfileBtn.setOnClickListener{
           val intent = Intent(this,CreateProfileActivity::class.java)
           startActivity(intent)
       }

       val updatePasswordBtn = findViewById<Button>(R.id.btn_update_email_password)
       updateProfileBtn.setOnClickListener{
           val intent = Intent(this,UserSettings::class.java)
           startActivity(intent)
       }
    }

}