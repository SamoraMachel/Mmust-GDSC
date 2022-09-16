package com.data.models

data class Registration (
    val email : String,
    val password : String,
    val fullname : String,
    val interests : List<String>
    val title : String = "Member"
)