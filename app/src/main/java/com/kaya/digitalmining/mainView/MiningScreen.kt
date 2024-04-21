package com.kaya.digitalmining.mainView

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaya.digitalmining.R
import com.kaya.digitalmining.model.Miner
import com.kaya.digitalmining.service.AdMobRewardedAd
import com.kaya.digitalmining.service.FirebaseImplementor
import com.kaya.digitalmining.util.BottomSheetScreen
import com.kaya.digitalmining.util.ClickableText
import com.kaya.digitalmining.util.CustomProgressDialog
import com.kaya.digitalmining.util.getStringResource
import com.kaya.digitalmining.viewModel.CountDownTimerViewModel
import com.kaya.digitalmining.viewModel.MinerViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun MiningScreen(context: Context) {

    val minerViewModel = viewModel<MinerViewModel>()
    val firebaseImplementor by lazy { FirebaseImplementor() }
    val countDownTimerViewModel = viewModel { CountDownTimerViewModel(context) }

    val showDialog = remember { mutableStateOf(false) }
    val showSheet = remember { mutableStateOf(false) }

    val clipboardManager = LocalClipboardManager.current
    val sdkNetworkWalletAddress = "Wallet ID"
    val adListenerViewModel = AdMobRewardedAd()

    if (showSheet.value) {
        BottomSheetScreen(context) {
            showSheet.value = false
        }
    }

    minerViewModel.getMinerData()
    minerViewModel.getTotalSdkNetworkAmount()

    minerViewModel.minerData.observe(LocalLifecycleOwner.current){
        it?.let { miner ->
            countDownTimerViewModel.startCountDownTimer(context, miner.initDate)
        } ?: run {
            countDownTimerViewModel.timerText.value = context.getText(R.string.tap_to_mine).toString()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(Color(0xff154360))
            }
            .padding(16.dp),
    ) {

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
                    .border(4.dp, Color(0xff58D68D), shape = CircleShape)
            )

            Button(
                onClick = {
                    if (countDownTimerViewModel.diffTime.longValue <= 0L) {
                        showDialog.value = true
                        adListenerViewModel.loadRewardedAd(context) { isLoaded ->
                            if(isLoaded){
                                showDialog.value = false
                                adListenerViewModel.showRewardedAd(context as Activity) {
                                    if(it){
                                        firebaseImplementor.firebaseAuth?.currentUser?.let { fbUser ->
                                            val minerId = UUID.randomUUID().toString()
                                            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                            val currentDate = Date()
                                            val formattedDate = dateFormat.format(currentDate)
                                            val miner = Miner(minerId, fbUser.uid, formattedDate.toString(), 10)
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
                    text = if(countDownTimerViewModel.diffTime.longValue == 0L && minerViewModel.minerData.value != null) getStringResource(context,R.string.synchronization___) else countDownTimerViewModel.timerText.value.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color(0xff58D68D)
                )
            }
        }

        Text(
            text = minerViewModel.totalSDKNetworkAmount.intValue.toString() + " " + "SDKN",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xff58D68D)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.wallet),
                contentDescription = "Wallet Icon",
                tint = Color.Gray,
                modifier = Modifier
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            // Add user wallet address
            ClickableText(text = sdkNetworkWalletAddress) {
                clipboardManager.setText(AnnotatedString(sdkNetworkWalletAddress))
                Toast.makeText(context, context.getText(R.string.copied),Toast.LENGTH_SHORT).show()
            }
        }

        Column(
            modifier =  Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            ElevatedButton(
                onClick = { showSheet.value = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = context.getText(R.string.mining_speed).toString())
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
