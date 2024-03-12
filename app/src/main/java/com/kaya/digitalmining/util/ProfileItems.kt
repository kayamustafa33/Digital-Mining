package com.kaya.digitalmining.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaya.digitalmining.R


@Composable
fun ProfileItems(cardColors: Int, boxIcon: Int, rowText: String){
    Row(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(50.dp),
            colors = CardColors(
                Color(cardColors),
                Color(cardColors),
                Color(cardColors),
                Color(cardColors)
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            ),
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = boxIcon),
                    contentDescription = null
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 20.dp),
        ) {
            Text(
                text = rowText,
                fontSize = 20.sp,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
    }

}

@Preview
@Composable
fun Show(){
    ProfileItems(0xff5DADE2.toInt(), R.drawable.wallet, "Wallet");
}