package com.example.jwtdemo.models

data class AuthenticationRequest(
    val username: String,
    val password: String
)