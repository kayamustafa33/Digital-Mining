package com.kaya.digitalmining.authView

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kaya.digitalmining.mainView.HomeScreen
import com.kaya.digitalmining.ui.theme.DigitalMiningTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "loginScreen" ){
                composable("signUpScreen"){ SignUpScreen(navController = navController,this@AuthActivity)}
                composable("loginScreen"){ LoginScreen(navController = navController, this@AuthActivity)}
            }
        }
    }
}