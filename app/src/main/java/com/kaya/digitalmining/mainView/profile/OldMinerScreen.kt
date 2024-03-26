package com.kaya.digitalmining.mainView.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaya.digitalmining.R
import com.kaya.digitalmining.model.OldMiner
import com.kaya.digitalmining.util.CustomProgressDialog
import com.kaya.digitalmining.viewModel.MinerViewModel
import kotlinx.coroutines.delay

@Composable
fun OldMinerScreen(){

    val oldMinerList = remember { mutableStateListOf<OldMiner>() }
    val oldMinerData by lazy { MinerViewModel() }
    val localLifecycleOwner = LocalLifecycleOwner.current
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        oldMinerData.getOldMinerData()
        isLoading.value = true
    }

    LaunchedEffect(Unit) {
        delay(500)
        oldMinerData.oldMinerData.observe(localLifecycleOwner){ listData ->
            listData?.let {
                oldMinerList.clear()
                oldMinerList.addAll(it)
                isLoading.value = false
            }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ){
        Text(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 10.dp, bottom = 10.dp), text = "Mining History", style = TextStyle(Color.White, fontSize = 18.sp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading.value) {
                CustomProgressDialog(isVisible = true)
            } else {
                OldMinerOrder(oldMinerList)
            }
        }
    }
}

@Composable
private fun OldMinerOrder(oldMinerList : MutableList<OldMiner>){
    LazyColumn {
        items(oldMinerList.size) { index ->
            Card (
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(0.dp),
                elevation = CardDefaults.cardElevation(3.dp),
                colors = CardDefaults.cardColors(Color(0xff212F3D)),
            ){
                Spacer(Modifier.height(3.dp))
                Row (Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.app_logo),
                        contentDescription = "Coin Logo",
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "SDK Network", style = TextStyle(color = Color.White))
                    Spacer(modifier = Modifier.weight(1F))
                    Text(text = oldMinerList[index].initDate, style = TextStyle(color = Color.Gray))
                }
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray))
                Spacer(Modifier.height(5.dp))
                Row (Modifier.padding(8.dp)) {
                    Text(text = "Order Quantity", style = TextStyle(color = Color.Gray))
                    Spacer(modifier = Modifier.weight(1F))
                    Text(text = oldMinerList[index].sdkCoinAmount.toString() + " " + "SDKN", style = TextStyle(color = Color.White))
                }
                Spacer(Modifier.height(5.dp))
                Row (Modifier.padding(8.dp)) {
                    Text(text = "Total", style = TextStyle(color = Color.Gray))
                    Spacer(modifier = Modifier.weight(1F))
                    Text("0 $", style = TextStyle(color = Color.White))
                }
                Spacer(Modifier.height(5.dp))
                Row (Modifier.padding(8.dp)) {
                    Text(text = "Taker/Maker", style = TextStyle(color = Color.Gray))
                    Spacer(modifier = Modifier.weight(1F))
                    Text("Maker", style = TextStyle(color = Color.White))
                }
                Spacer(Modifier.height(3.dp))
            }
        }
    }
}

@Preview
@Composable
private fun Show(){
    OldMinerScreen()
}