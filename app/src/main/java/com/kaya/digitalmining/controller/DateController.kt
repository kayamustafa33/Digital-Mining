package com.kaya.digitalmining.controller

import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import com.kaya.digitalmining.R
import com.kaya.digitalmining.util.getString
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateController {

    private var countDownTimer: CountDownTimer? = null
    val remain = MutableLiveData<String>()
    val hashVisibility = MutableLiveData<Boolean>()
    var diffState  = MutableLiveData<Long>()

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    fun countDownTimer(initDate: String,context: Context) {
        val targetDate = Calendar.getInstance().apply {
            time = formatter.parse(initDate)!!
            add(Calendar.HOUR_OF_DAY, 4)
        }

        CoroutineScope(Dispatchers.Main).launch {
            val diff = targetDate.timeInMillis - System.currentTimeMillis()
            diffState.value = diff

            if (diff > 0) {
                countDownTimer?.cancel()
                countDownTimer = object : CountDownTimer(diff, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val seconds = millisUntilFinished / 1000
                        val minutes = seconds / 60
                        val hours = minutes / 60
                        val remainingMinutes = minutes % 60
                        val remainingSeconds = seconds % 60
                        remain.value = String.format("%02d:%02d:%02d", hours, remainingMinutes, remainingSeconds)
                    }

                    override fun onFinish() {
                        remain.value = context.getString(R.string.tap_to_mine)
                        hashVisibility.value = false
                    }
                }
                countDownTimer?.start()
            } else {
                remain.value = context.getString(R.string.tap_to_mine)
                hashVisibility.value = false
            }
        }
    }
}
