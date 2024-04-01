package com.kaya.digitalmining.util

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kaya.digitalmining.navigation.Screen

@Composable
fun SuccessScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181A20)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 35.dp)
                .size(64.dp),
            tint = Color(0xff32CD32)
        )

        Text(
            text = "Email was successfully sent!",
            modifier = Modifier.padding(start = 15.dp, top = 10.dp),
            fontSize = 21.sp,
            color = Color.White,
            fontWeight = FontWeight.W500
        )

        Text(
            text = "The password reset link has been sent to your e-mail address. You need to reset your password. If you did not receive the request, ignore this email. Contact us with any questions. Thank you.",
            modifier = Modifier.padding(start = 15.dp, top = 5.dp, end = 15.dp),
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W400
        )

        Button(
            onClick = {
                navController.popBackStack()
                navController.navigate("${Screen.SuccessScreen.route}/${Screen.ProfileScreen.route}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 15.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFF0B90B))
        ) {
            Text(text = "Complete")
        }

    }

}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewSuccessScreen() {
    SuccessScreen(navController = rememberNavController())
}