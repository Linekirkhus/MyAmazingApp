package com.example.myamazingapp.model

data class User(
    val name: String,
    val bio: String,
    val profilePhotoPath: String?) {

    constructor(): this("", "", null)
}