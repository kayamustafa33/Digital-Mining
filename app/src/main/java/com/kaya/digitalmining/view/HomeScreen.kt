package com.kaya.digitalmining.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kaya.digitalmining.R

@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Home Screen")

        Button(
            onClick = { navController.navigate("miningScreen") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Go to Mining Screen")
        }
    }
}