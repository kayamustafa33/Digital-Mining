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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaya.digitalmining.R
import com.kaya.digitalmining.util.FeedbackItems
import com.kaya.digitalmining.util.SettingsItems

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF313131))
    ) {
        Text(
            text = stringResource(id = R.string.settings),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = largePadding, start = mediumPadding, bottom = mediumPadding),
            textAlign = TextAlign.Start,
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Divider(color = Color.DarkGray)

        SettingsSection(title = "General") {
            SettingsItems()
        }

        SettingsSection(title = "Feedback") {
            FeedbackItems()
        }
    }
}

@Composable
private fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = title,
            modifier = Modifier
                .wrapContentSize()
                .padding(start = mediumPadding, bottom = smallPadding, top = largePadding),
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            color = Color.White
        )
        content()
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewSettingsScreen() {
    SettingsScreen()
}

val largePadding = 35.dp
val mediumPadding = 25.dp
val smallPadding = 10.dp