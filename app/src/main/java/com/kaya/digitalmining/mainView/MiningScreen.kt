package com.kaya.digitalmining.mainView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaya.digitalmining.controller.DateController
import com.kaya.digitalmining.controller.HashController
import com.kaya.digitalmining.util.SubsCardItem

@Composable
fun MiningScreen(){
    val dateController = DateController()
    dateController.countDownTimer("2024-02-29 13:40:00")
    val hashController = HashController()

    val cardList = remember { List(6) { index ->  "Item $index" } }

    var remain by remember { mutableStateOf("") }
    var diffState by remember { mutableLongStateOf(0L) }
    var hashVisibility by remember { mutableStateOf(true) }
    var hash by remember{ mutableStateOf("") }

    dateController.remain.observe(LocalLifecycleOwner.current){
        remain = it
    }

    dateController.diffState.observe(LocalLifecycleOwner.current){
        diffState = it
    }

    dateController.hashVisibility.observe(LocalLifecycleOwner.current){
        hashVisibility = it
    }

    hashController.hashCoinText(diffState){
        hash = it
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
            Text(
                text = if(diffState > 0) hash else "",
                color = Color.Gray,
                modifier = Modifier
                    .alpha(if (hashVisibility) 1f else 0f)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp)
            )

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp)
        ) {
            CircularProgressIndicator(
                progress = { 1F },
                modifier = Modifier
                    .size(200.dp),
                color = Color(0XFFF39C12),
            )
            Text(
                text = remain,
                modifier = Modifier
                    .align(Alignment.Center),
                fontSize = 20.sp,
                color = Color(0XFFF5B041)
            )
        }

        Text(
            text = "Current SDK Coin",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.padding(top = 30.dp)
        ) {
            items(cardList){
                SubsCardItem(text = it, LocalContext.current)
            }
        }
    }
}

@Preview
@Composable
fun ShowMiningScreen(){
    MiningScreen()
}