package com.kaya.digitalmining.authView

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kaya.digitalmining.mainView.privacy.PrivacyScreen
import com.kaya.digitalmining.navigation.Screen

class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "loginScreen" ){
                composable(Screen.SignUpScreen.route){ SignUpScreen(navController,this@AuthActivity)}
                composable(Screen.LoginScreen.route){ LoginScreen(navController = navController, this@AuthActivity)}
                composable(Screen.PrivacyScreen.route) { PrivacyScreen(navController = navController) }
            }
        }
    }
}