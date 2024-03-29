package com.kaya.digitalmining.mainView.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaya.digitalmining.R

@Composable
fun RoadmapScreen(){

    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .wrapContentSize(Alignment.Center),
    ) {
        Image(
            painterResource(id = R.drawable.sdkn_roadmap) ,
            contentDescription = "SDKN Roadmap",
            Modifier.padding(8.dp).fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "More information", modifier = Modifier.align(Alignment.CenterHorizontally), style = TextStyle(color = Color.Black, fontSize = 18.sp))
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painterResource(id = R.drawable.arrow_line) ,
            contentDescription = "SDKN Roadmap",
            Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
                .rotate(50f)
        )
    }

    Box (modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.BottomCenter)
        .padding(bottom = 15.dp, start = 20.dp, end = 20.dp),
        contentAlignment = Alignment.BottomCenter)
    {
        ElevatedButton(
            onClick = {
                // Download pdf document
                Toast.makeText(context,"Artık milyonersin tosunum.",Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Download White Paper")
        }
    }
}




@Preview
@Composable
private fun Show(){
    RoadmapScreen()
}