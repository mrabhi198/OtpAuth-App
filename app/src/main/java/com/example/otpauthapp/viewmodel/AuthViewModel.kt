package com.example.otpauthapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.otpauthapp.analytics.AnalyticsLogger
import com.example.otpauthapp.data.OtpManager
import com.example.otpauthapp.data.OtpRes

class AuthViewModel: ViewModel() {
    private val otpManager= OtpManager()
    var state by mutableStateOf(AuthState())
        private set

    fun sendOtp(email: String){
        otpManager.generateOtp(email)
        AnalyticsLogger.otpGenerated(email)
        state = state.copy(email = email, isOtpSent = true, error = null)
    }

    fun verifyOtp(otp: String){
        val success = otpManager.validateOtp(state.email, otp)
        when (success) {
            OtpRes.SUCCESS -> {
                AnalyticsLogger.otpSuccess(state.email)
                state = state.copy(
                    isLoggedIn = true,
                    loginTime = System.currentTimeMillis(),
                    error = null
                )
            }

            OtpRes.EXPIRED -> {
                state = state.copy(error = "OTP expired. Please resend OTP.")
            }

            OtpRes.EXCEEDED -> {
                state = state.copy(error = "OTP attempt limit exceeded. Please resend OTP.")
            }

            OtpRes.INVALID -> {
                AnalyticsLogger.otpFail(state.email)
                state = state.copy(error = "incorrect OTP. Please try again.")
            }
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