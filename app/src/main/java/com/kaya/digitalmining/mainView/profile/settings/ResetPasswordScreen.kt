package com.kaya.digitalmining.mainView.profile.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaya.digitalmining.R

@Composable
fun ResetPasswordScreen() {

    val confirmEmail = remember { mutableStateOf("") }
    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color(0xFFF0B90B),
        unfocusedBorderColor = Color.White
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181A20))
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(start = 5.dp, top = 15.dp)
                .size(48.dp)
        )

        Icon(
            painter = painterResource(id = R.drawable.baseline_lock_reset_24),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 15.dp, top = 35.dp)
                .size(48.dp),
            tint = Color.White
        )

        Text(
            text = "Reset Password",
            modifier = Modifier.padding(start = 15.dp, top = 10.dp),
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.W500
        )

        Text(
            text = "Please enter your email address below to reset your password. Follow the steps immediately to regain access to your account.",
            modifier = Modifier.padding(start = 15.dp, top = 5.dp, end = 15.dp),
            fontSize = 16.sp,
            color = Color.Gray,
            fontWeight = FontWeight.W500
        )

        OutlinedTextField(
            value = confirmEmail.value,
            onValueChange = { confirmEmail.value = it },
            singleLine = true,
            textStyle = TextStyle(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 25.dp),
            colors = textFieldColors,
            placeholder = {
                Text(
                    text = "E-mail",
                    color = Color.White
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = "E-mail",
                    tint = Color.White
                )
            }
        )

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFF0B90B))
        ) {
            Text(text = "Confirm")
        }

    }


}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewResetPassword() {
    ResetPasswordScreen()
}