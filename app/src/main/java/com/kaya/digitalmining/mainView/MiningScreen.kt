package com.kaya.digitalmining.mainView

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaya.digitalmining.R
import com.kaya.digitalmining.controller.DateController
import com.kaya.digitalmining.controller.HashController
import com.kaya.digitalmining.model.Miner
import com.kaya.digitalmining.service.AdMobRewardedAd
import com.kaya.digitalmining.service.FirebaseImplementor
import com.kaya.digitalmining.util.CustomProgressDialog
import com.kaya.digitalmining.util.SubsCardItem
import com.kaya.digitalmining.viewModel.MinerViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun MiningScreen(context: Context) {

    val minerViewModel by lazy { MinerViewModel() }
    val dateController by lazy { DateController(context) }
    val hashController by lazy { HashController() }
    val firebaseImplementor by lazy { FirebaseImplementor() }

    val cardInfoList: List<Triple<String, String, Context>> = listOf(
        Triple("Mining rate 15", "3$", context),
        Triple("Mining rate 20", "5$", context),
        Triple("Mining rate 25", "8$", context),
        Triple("Mining rate 30", "10$", context),
        Triple("Mining rate 35", "13$", context),
        Triple("Mining rate 40", "15$", context)
    )

    val remain = remember { mutableStateOf(context.getString(R.string.synchronization___)) }
    val diffState = remember { mutableLongStateOf(0L) }
    val hashVisibility = remember { mutableStateOf(true) }
    val hash = remember { mutableStateOf("") }
    val sdkCoin = remember { mutableIntStateOf(0) }
    val localLifecycleOwner = LocalLifecycleOwner.current
    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        remain.value = context.getString(R.string.synchronization___)
        delay(1000)
    }

    val adListenerViewModel = AdMobRewardedAd()

    LaunchedEffect(Unit) {

        minerViewModel.getMinerData()
        minerViewModel.getTotalSdkNetworkAmount()

        dateController.remain.observe(localLifecycleOwner) {
            remain.value = it
        }
        hashController.hashCoinText(diffState.longValue) {
            hash.value = it
        }

        dateController.diffState.observe(localLifecycleOwner) {
            diffState.longValue = it
        }

        dateController.hashVisibility.observe(localLifecycleOwner) {
            hashVisibility.value = it
        }

        minerViewModel.minerData.observe(localLifecycleOwner) { data ->
            data?.let { dateController.countDownTimer(it.initDate) }
        }

        minerViewModel.totalSDKNetworkAmount.observe(localLifecycleOwner) { total ->
            sdkCoin.intValue = total
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = if (diffState.longValue > 0) hash.value else "",
            color = Color.Gray,
            modifier = Modifier
                .alpha(if (hashVisibility.value) 1f else 0f)
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Transparent, shape = CircleShape)
                    .border(2.dp, Color(0xFFFFA500), shape = CircleShape)
            )

            Button(
                onClick = {
                    if (diffState.longValue <= 0) {
                        showDialog.value = true
                        adListenerViewModel.loadRewardedAd(context) { isLoaded ->
                            if(isLoaded){
                                showDialog.value = false
                                adListenerViewModel.showRewardedAd(context as Activity) {
                                    if(it){
                                        firebaseImplementor.firebaseAuth?.currentUser?.let {
                                            val minerId = UUID.randomUUID().toString()
                                            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                            val currentDate = Date()
                                            val formattedDate = dateFormat.format(currentDate)
                                            val miner = Miner(minerId, firebaseImplementor.firebaseUser!!.uid, formattedDate.toString(), 10)
                                            minerViewModel.setMinerData(miner) {
                                                minerViewModel.setOldMinerData(miner)
                                            }
                                        }
                                    }
                                }
                            } else {
                                showDialog.value = false
                            }
                        }
                    }
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(199.dp)
            ) {
                Text(
                    text = if(diffState.longValue <= 0L && hashVisibility.value.not()) context.getString(R.string.tap_to_mine) else remain.value,
                    fontSize = 20.sp,
                    color = Color(0XFFF5B041)
                )
            }
        }

        Text(
            text = sdkCoin.intValue.toString(),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(cardInfoList) { item ->
                SubsCardItem(productName = item.first, price = item.second,item.third)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        contentAlignment = Alignment.Center
    ) {
        if(showDialog.value){
            CustomProgressDialog(isVisible = true)
        }
    }
}

@Preview
@Composable
fun ShowMiningScreenPreview() {
    MiningScreen(LocalContext.current)
}
