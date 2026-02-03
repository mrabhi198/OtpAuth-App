package com.example.otpauthapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.otpauthapp.ui.screen.LoginScreen
import com.example.otpauthapp.ui.screen.OtpScreen
import com.example.otpauthapp.ui.screen.SessionScreen
import com.example.otpauthapp.ui.theme.OtpAuthAppTheme
import com.example.otpauthapp.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = remember { AuthViewModel() }
            val state = viewModel.state

            when {
                state.isLoggedIn ->
                    SessionScreen(state.loginTime, viewModel::logout)

                state.isOtpSent->
                    OtpScreen(state.error, viewModel::verifyOtp)

                else ->
                    LoginScreen(viewModel::sendOTP)
            }
        }
    }
}
