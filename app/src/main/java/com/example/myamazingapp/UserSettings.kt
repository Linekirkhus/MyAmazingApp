package com.example.myamazingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user_settings.*

class UserSettings : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        auth = FirebaseAuth.getInstance()

        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            val intent = Intent (this, DashboardActivity::class.java)
            startActivity(intent)
        }

        val btnSavePassword = findViewById<Button>(R.id.btn_save_password)
        btnSavePassword.setOnClickListener {

            changePassword()
        }
    }

    private fun changePassword() {
        if (et_current_password.text.isNotEmpty() &&
            et_new_password.text.isNotEmpty() &&
            et_confirm_password.text.isNotEmpty()
        ) {
            if (et_new_password.text.toString().equals(et_confirm_password.text.toString())) {

                val user = auth.currentUser
                if (user != null && user.email != null) {
                    // Get auth credentials from the user for re-authentication. The example below shows
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, et_current_password.text.toString())

                    // Prompt the user to re-provide their sign-in credentials
                    user?.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Re-Authentication Success",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()


                                user!!.updatePassword(et_new_password.text.toString())
                                    ?.addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                this,
                                                "Password update successful",
                                                Toast.LENGTH_SHORT)
                                                .show()

                                            auth.signOut()
                                            startActivity(Intent(this, LoginActivity::class.java))
                                            finish()
                                        }
                                    }
                            } else {
                                Toast.makeText(this, "Re-Authentication Failed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            } else {
                Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT)
                .show()

        }

    }
}
