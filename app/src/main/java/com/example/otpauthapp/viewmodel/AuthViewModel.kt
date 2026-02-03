package com.example.otpauthapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.otpauthapp.analytics.AnalyticsLogger
import com.example.otpauthapp.data.OtpManager

class AuthViewModel: ViewModel() {
    private val otpManager= OtpManager()
    var state by mutableStateOf(AuthState())
        private set

    fun sendOtp(email: String){
        otpManager.generateOtp(email)
        AnalyticsLogger.otpGenerated(email)
        state = state.copy(email, isOtpSent = true, error = null)
    }

    fun verifyOtp(otp: String){
        val success = otpManager.validateOtp(state.email, otp)
        if (success){
            AnalyticsLogger.otpSuccess(state.email)
            state = state.copy(
                isLoggedIn = true,
                loginTime = System.currentTimeMillis()
            )
        }
        else {
            AnalyticsLogger.otpFail(state.email)
            state = state.copy(error = "invalid or expired OTP")
        }
    }

    fun resendOtp(){
        otpManager.generateOtp(state.email)
        AnalyticsLogger.otpGenerated(state.email)

        state = state.copy(
            error = null
        )
    }

    fun logout(){
        AnalyticsLogger.logout(state.email)
        state = AuthState()
    }
}