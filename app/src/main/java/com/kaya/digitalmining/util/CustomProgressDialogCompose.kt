package com.kaya.digitalmining.util


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomProgressDialog(isVisible: Boolean) {
    if (isVisible) {
        Box (contentAlignment = Alignment.Center){
            CircularProgressIndicator(
                modifier = Modifier
                    .size(60.dp),
                color = Color(0xFFFFA500)
            )
            CircularProgressIndicator(
                modifier = Modifier
                    .size(45.dp)
                    .rotate(180f),
                color = Color(0xFFFFA500),
            )
        }

    }
}

@Preview
@Composable
fun PreviewCustomProgressDialog() {
    CustomProgressDialog(isVisible = true)
}
