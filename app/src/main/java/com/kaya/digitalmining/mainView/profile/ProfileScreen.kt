package com.kaya.digitalmining.mainView.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kaya.digitalmining.R
import com.kaya.digitalmining.authView.AuthActivity
import com.kaya.digitalmining.controller.Auth
import com.kaya.digitalmining.util.ProfileItems

@Composable
fun ProfileScreen(context: Context, navController: NavController) {

    val profileCardItems = listOf(
        Triple(0xff5DADE2.toInt(), R.drawable.wallet, "Wallet"),
        Triple(0xff82E0AA.toInt(), R.drawable.history, "Mining History"),
        Triple(0xffF5B041.toInt(), R.drawable.settings, "Settings"),
        Triple(0xFF000000.toInt(), R.drawable.baseline_logout_24, "Logout")
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.geometric_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = null,
                modifier = Modifier
                    .padding(30.dp)
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )

            Text(text = "User ID", style = TextStyle(color = Color.White, fontSize = 20.sp))

            Spacer(modifier = Modifier.padding(top = 50.dp))
        Card (
            modifier = Modifier
                .weight(1f),
            colors = CardColors(MaterialTheme.colorScheme.background,MaterialTheme.colorScheme.background,MaterialTheme.colorScheme.background,MaterialTheme.colorScheme.background),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ){
            LazyColumn {
                profileCardItems.forEachIndexed { index, (cardColors, boxIcon, rowText) ->
                    item {
                        Surface (
                            color = MaterialTheme.colorScheme.background,
                            onClick = {
                                profileDestination(navController = navController, context = context, page = profileCardItems[index].third)
                                Toast.makeText(context, profileCardItems[index].third,Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            ProfileItems(cardColors = cardColors, boxIcon = boxIcon, rowText = rowText)
                        }

                    }
                }
            }
        }
        }
    }
}

private fun profileDestination(navController: NavController, context: Context, page: String) {
    when(page) {
        "Wallet" -> navController.navigate("profileScreen/walletScreen")
        "Mining History" -> {}
        "Settings" -> {}
        "Logout" -> {
            val auth = Auth()
            auth.logout()
            val activity = context as? Activity
            activity?.finish()
            val intent = Intent(context, AuthActivity::class.java)
            context.startActivity(intent)
        }
    }
}


@Preview
@Composable
fun Test(){
    ProfileScreen(LocalContext.current,navController = rememberNavController())
}