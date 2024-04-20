package com.kaya.digitalmining.mainView.privacy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kaya.digitalmining.R
import com.kaya.digitalmining.navigation.Screen
import com.kaya.digitalmining.util.getString

@Composable
fun PrivacyScreen(navController: NavController){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .drawBehind {
                drawRect(Color.White)
            }
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = getString(id = R.string.privacy_policy),
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp
        )

        ElevatedButton(
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            onClick = {
                navController.navigate(Screen.SignUpScreen.route) {
                    popUpTo(navController.graph.id){
                        inclusive = true
                    }
                }
            }
        ){
            Text(text = "Agree")
        }
    }
}