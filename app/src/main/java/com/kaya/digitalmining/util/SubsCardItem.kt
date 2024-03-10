package com.kaya.digitalmining.util

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SubsCardItem(text: String, context: Context) {
    Card(
        modifier = Modifier.padding(8.dp)
            .height(100.dp),
        colors = CardColors(Color(0xFF283747),Color(0xFF283747),Color(0xFF283747),Color(0xFF283747)),
        onClick = {
            Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}


@Preview
@Composable
fun ShowCard(){
    SubsCardItem(text = "it", LocalContext.current)
}