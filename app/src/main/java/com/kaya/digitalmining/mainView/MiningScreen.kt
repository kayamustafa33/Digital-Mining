package com.kaya.digitalmining.mainView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MiningScreen(navController : NavController){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Mining Screen")

        Button(
            onClick = { navController.navigate("homeScreen"){
                popUpTo("miningScreen"){
                    inclusive = true
                }
            } },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Go to Home Screen")
        }
    }
}