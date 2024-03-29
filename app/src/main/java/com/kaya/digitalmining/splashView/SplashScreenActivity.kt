package com.kaya.digitalmining.splashView

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaya.digitalmining.R
import com.kaya.digitalmining.authView.AuthActivity
import com.kaya.digitalmining.controller.Auth
import com.kaya.digitalmining.mainView.MainActivity
import kotlinx.coroutines.delay

class SplashScreenActivity : ComponentActivity() {

    private val auth = Auth()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val progress = remember { mutableIntStateOf(0) }
            LaunchedEffect(Unit) {
                while (progress.intValue < 100) {
                    delay(25)
                    progress.intValue++
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff283747))
                    .wrapContentSize(Alignment.Center),
                contentAlignment = Alignment.Center
            ){
                Image( painterResource(id = R.drawable.app_logo), contentDescription = "App Logo")
                CircularProgressIndicator(
                    progress = { progress.intValue / 100f },
                    color = Color.White,
                    strokeWidth = 4.dp,
                    modifier = Modifier.size(200.dp)
                )
            }

            if(progress.intValue == 100){
                if (auth.isLogin()) {
                    Intent(this@SplashScreenActivity, MainActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                } else {
                    Intent(this@SplashScreenActivity, AuthActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SplashScreenActivity()
}