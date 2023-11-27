package com.example.bostatask_1.model

data class UserData(
    val id: String,
    val name: String,
    val address: String,
    val albumList: List<String>? = null
)
