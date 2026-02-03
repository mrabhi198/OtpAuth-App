package com.example.otpauthapp.data

import timber.log.Timber
import kotlin.random.Random

data class OtpData(
    val otp: String,
    val timeStamp: Long,
    var attempts: Int
)

class OtpManager{
    private val otpStore= mutableMapOf<String, OtpData>()
    private val EXPIRY = 60_000L
    private val MAX_ATTEMPTS = 3

    fun generateOtp(email: String): String{
        val otp = Random.nextInt(100000, 999999).toString()
        otpStore[email] = OtpData(otp, System.currentTimeMillis(), 0)

        Timber.d("generated OTP for $email is $otp")
        return otp
    }
    fun validateOtp(email : String, input: String): Boolean{
        val data = otpStore[email]?: return false
        if(System.currentTimeMillis() -data.timeStamp > EXPIRY){
            return false
        }

        if (data.attempts>=MAX_ATTEMPTS){
            return false
        }
        data.attempts++
        return data.otp == input
    }
}