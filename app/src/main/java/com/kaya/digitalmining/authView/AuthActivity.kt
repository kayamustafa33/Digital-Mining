package com.kaya.digitalmining.authView

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "loginScreen" ){
                composable("signUpScreen"){ SignUpScreen(navController,this@AuthActivity)}
                composable("loginScreen"){ LoginScreen(navController = navController, this@AuthActivity)}
            }
        }
    }
}