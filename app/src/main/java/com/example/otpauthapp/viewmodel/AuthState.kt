package com.example.otpauthapp.viewmodel

data class AuthState(
    val email: String = "",
    val isOtpSent: Boolean= false,
    val isLoggedIn: Boolean= false,
    val loginTime: Long = 0L,
    val error: String? = null
)