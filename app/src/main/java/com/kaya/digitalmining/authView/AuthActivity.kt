package com.kaya.digitalmining.authView

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kaya.digitalmining.controller.Auth
import com.kaya.digitalmining.mainView.MainActivity

class AuthActivity : ComponentActivity() {

    private val auth = Auth()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "loginScreen" ){
                composable("signUpScreen"){ SignUpScreen(this@AuthActivity)}
                composable("loginScreen"){ LoginScreen(navController = navController, this@AuthActivity)}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (auth.isLogin()) {
            Intent(this@AuthActivity, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

}