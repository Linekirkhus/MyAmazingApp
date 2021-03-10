package com.example.myamazingapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CreateProfileActivity : AppCompatActivity(){
	
	//private lateinit var editProfilePicture: FloatingActionButton
	private lateinit var storage: FirebaseStorage
	private lateinit var db: FirebaseFirestore
	private lateinit var storageReference: StorageReference
	
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_create_profile)
		//profilePicture = findViewById(R.id.iv_cp_profile_picture)
		
		db = FirebaseFirestore.getInstance()
		storage = FirebaseStorage.getInstance()
		storageReference = storage.getReference()
		
	val	editProfilePicture = findViewById<FloatingActionButton>(R.id.fab_btn_cp_update_profile_image)
		editProfilePicture.setOnClickListener {
			val intent = Intent(this, UploadPhotoFragment::class.java)
			startActivity(intent)
		}
	}
}
