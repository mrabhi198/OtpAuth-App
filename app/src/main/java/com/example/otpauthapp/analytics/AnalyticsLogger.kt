package com.example.otpauthapp.analytics

import timber.log.Timber

object AnalyticsLogger {
    fun otpGenerated(email : String){
        Timber.d("OTP generated for $email")
    }

    fun otpSuccess (email: String){
        Timber.d("OTP success")
    }

    fun otpFail(email: String){
        Timber.d("OTP failed")
    }

    fun logout(email: String){
        Timber.d("User logged out")
    }
}