package com.kaya.digitalmining.mainView

import android.content.Context
import android.opengl.Visibility
import android.text.TextUtils
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaya.digitalmining.R
import com.kaya.digitalmining.controller.DateController
import com.kaya.digitalmining.controller.HashController
import com.kaya.digitalmining.model.Miner
import com.kaya.digitalmining.service.FirebaseImplementor
import com.kaya.digitalmining.util.SubsCardItem
import com.kaya.digitalmining.util.getString
import com.kaya.digitalmining.viewModel.MinerViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun MiningScreen(context: Context){

    val minerViewModel = viewModel<MinerViewModel>()
    val firebaseImplementor = FirebaseImplementor()

    val cardList = remember { List(6) { index ->  "Item $index" } }

    var remain by remember { mutableStateOf(context.getString(R.string.synchronization___)) }
    var diffState by remember { mutableLongStateOf(0L) }
    var hashVisibility by remember { mutableStateOf(true) }
    var hash by remember{ mutableStateOf("") }
    var sdkCoin by remember { mutableIntStateOf(0) }

    val dateController = DateController()
    val hashController = HashController()

    // Can not take any data without user login!
    minerViewModel.getMinerData()
    minerViewModel.getTotalSdkNetworkAmount()

    minerViewModel.minerData.observe(LocalLifecycleOwner.current){data ->
        if(data != null){
            dateController.countDownTimer(data.initDate, context)
        }
    }

    minerViewModel.totalSDKNetworkAmount.observe(LocalLifecycleOwner.current){ total ->
        sdkCoin = total
    }

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

            Button(
                onClick = {
                    if(diffState <= 0){
                        with(firebaseImplementor){
                            firebaseAuth?.currentUser?.let {
                                val minerId = UUID.randomUUID().toString()
                                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                val currentDate = Date()
                                val formattedDate = dateFormat.format(currentDate)
                                val miner = Miner(minerId,firebaseUser!!.uid,formattedDate.toString(),10)
                                minerViewModel.setMinerData(miner){
                                    //Show ads and register old miner data
                                    minerViewModel.setOldMinerData(miner)
                                }
                            }
                        }
                    }
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(199.dp)
                ) {
                Text(
                    text = if(TextUtils.isEmpty(remain).not()) remain else getString(id = R.string.tap_to_mine),
                    fontSize = 20.sp,
                    color = Color(0XFFF5B041)
                )
            }
        }

        Text(
            text = sdkCoin.toString(),
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
    MiningScreen(LocalContext.current)
}