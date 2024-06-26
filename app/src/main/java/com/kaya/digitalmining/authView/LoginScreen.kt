package com.kaya.digitalmining.authView

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kaya.digitalmining.R
import com.kaya.digitalmining.controller.Auth
import com.kaya.digitalmining.mainView.MainActivity
import com.kaya.digitalmining.model.User
import com.kaya.digitalmining.navigation.Screen
import com.kaya.digitalmining.util.CustomProgressDialog
import com.kaya.digitalmining.util.getString

@Composable
fun LoginScreen(navController : NavController, context: Context) {

    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = getString(id = R.string.sign_in),
            color = Color.Black,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = emailState.value,
            onValueChange = { newValue -> emailState.value = newValue },
            label = { Text(text = getString(id = R.string.email)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

        OutlinedTextField(
            value = passwordState.value,
            onValueChange = { newValue -> passwordState.value = newValue },
            label = {Text(text = getString(id = R.string.password)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(
                onDone = null )
        )

        Button(
            colors = ButtonColors(Color(0XFFF39C12),Color.White, Color(0XFFF39C12), Color(0XFFF39C12)),
            onClick = {
                if (emailState.value.isNotEmpty() && passwordState.value.isNotEmpty()) {
                    showDialog.value = true
                    val user = User(emailState.value, passwordState.value)
                    val authentication = Auth()
                    emailState.value = ""
                    passwordState.value = ""
                    authentication.authUser(user) {
                        if (it) {
                            showDialog.value = false
                            val activity = context as Activity
                            Intent(activity, MainActivity::class.java).also { intent ->
                                activity.startActivity(intent)
                                activity.finish()
                            }
                        }else {
                            showDialog.value = false
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Text(text = getString(id = R.string.sign_in))
        }

        Surface (onClick = {
            navController.navigate(Screen.SignUpScreen.route) {
                popUpTo(Screen.LoginScreen.route) {
                    inclusive = true
                }
            }
        }) {
            Text(
                text = getString(id = R.string.dont_have_account),
                style = TextStyle(textDecoration = TextDecoration.None)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        contentAlignment = Alignment.Center
    ) {
        if(showDialog.value){
            CustomProgressDialog(isVisible = true)
        }
    }

}


@Preview
@Composable
fun PreviewLoginPage() {
    val navController = rememberNavController()
    val context = LocalContext.current
    LoginScreen(navController, context)
}