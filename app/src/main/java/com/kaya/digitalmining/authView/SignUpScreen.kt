package com.kaya.digitalmining.authView

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import com.kaya.digitalmining.util.CustomProgressDialog
import com.kaya.digitalmining.util.getString


@Composable
fun SignUpScreen(navController : NavController, context: Context){
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .wrapContentHeight(Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = getString(id = R.string.sign_up),
            color = Color.Black,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = {email.value = it},
            label = { Text(text = "E-mail Address")},
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password,
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        OutlinedTextField(
            value = confirmPassword.value,
            onValueChange = { confirmPassword.value = it },
            label = { Text("Confirm Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )


        Button(
            onClick = {
                      if(email.value.isNotEmpty()
                          && password.value.isNotEmpty()
                          && confirmPassword.value.isNotEmpty()){

                          if(password.value == confirmPassword.value){
                              if(password.value.length >= 6){
                                  showDialog.value = true

                                  val user = User(email.value,password.value)
                                  val auth = Auth()

                                  email.value = ""
                                  password.value = ""
                                  confirmPassword.value = ""

                                  auth.registerUser(user){
                                      if(it){
                                          showDialog.value = false
                                          Intent(context,MainActivity::class.java).also { intent ->
                                              context.startActivity(intent)
                                          }
                                      }else {
                                          showDialog.value = false
                                      }
                                  }
                              } else {
                                  showDialog.value = false
                                  Toast.makeText(context,"Passwords length should be greater than 6!",Toast.LENGTH_SHORT).show()
                              }

                          } else {
                              showDialog.value = false
                              Toast.makeText(context,"Passwords don't matches!",Toast.LENGTH_SHORT).show()
                          }


                      } else {
                          showDialog.value = false
                      }
            },
            colors = ButtonColors(Color(0xFF3498DB),Color.White, Color(0xFF3498DB), Color(0xFF3498DB)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
            ) {
            Text(text = getString(id = R.string.sign_up))
        }

        Surface(onClick = {
            navController.navigate("loginScreen") {
                popUpTo("signUpScreen") {
                    inclusive = true
                }
            }
        }) {
            Text(
                text = getString(id = R.string.already_have_an_account),
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
fun PreviewSignUpPage() {
    val context = LocalContext.current
    val navController = rememberNavController()
    SignUpScreen(navController,context)
}