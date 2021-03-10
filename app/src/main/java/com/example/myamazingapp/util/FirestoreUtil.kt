package com.example.myamazingapp.util

import com.example.myamazingapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val currentUserDocumentRef: DocumentReference
        get() = firestoreInstance.document(
            "users/${
                FirebaseAuth.getInstance().currentUser?.uid ?: throw NullPointerException(
                    "UID is null"
                )
            }"
        )

    // higher order function if user already has logged in to app - TODO create this logic - then another fun will do something else..
    fun initUserIfFirstTime(onComplete: () -> Unit) {
        currentUserDocumentRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                val newUser =
                    User(FirebaseAuth.getInstance().currentUser?.displayName ?: "", "", null)
                currentUserDocumentRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else
                onComplete()
        }
    }

    fun updateCurrentUser(name: String = "", bio: String = "", profilePhotoPath: String? = null) {
        val userFieldMap = mutableMapOf<String, Any>()
        if (name.isNotBlank()) userFieldMap["name"] = name
        if (name.isNotBlank()) userFieldMap["bio"] = bio
        if (profilePhotoPath != null)
            userFieldMap["profilePhotoPath"] = profilePhotoPath
        currentUserDocumentRef.update(userFieldMap)
    }

    fun getCurrentUser(onComplete: (User) -> Unit) {
        currentUserDocumentRef.get().addOnSuccessListener {
             onComplete(it.toObject(User::class.java)!!)
        }
    }
}