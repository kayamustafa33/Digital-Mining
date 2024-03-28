package com.kaya.digitalmining.mainView.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaya.digitalmining.R
import com.kaya.digitalmining.util.SettingsItems

@Composable
fun SettingsScreen() {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF313131))) {
        Text(
            text = "Settings", // getString(id = R.string.settings),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 35.dp, start = 25.dp, bottom = 15.dp),
            textAlign = TextAlign.Start,
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Divider(color = Color.DarkGray)

        AccountSettingsUI()



    }

}

@Composable
fun AccountSettingsUI() {

    Column {
        Text(
            text = "General",
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 25.dp, bottom = 10.dp, top = 25.dp),
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            color = Color.White
        )

        SettingsItems()


    }

}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewSettingsScreen() {
    SettingsScreen()
}