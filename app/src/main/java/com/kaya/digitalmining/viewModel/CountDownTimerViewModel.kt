package com.kaya.digitalmining.viewModel

import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.kaya.digitalmining.R
import com.kaya.digitalmining.util.TimeFormatExt.timeFormat
import com.kaya.digitalmining.util.getStringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CountDownTimerViewModel(context: Context) : ViewModel(){

    private var countDownTimer: CountDownTimer? = null

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val diffTime = mutableLongStateOf(0L)
    var timerText = mutableStateOf(context.getText(R.string.synchronization___))
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun startCountDownTimer(context: Context, initDate: String) {
        val targetCalendar = Calendar.getInstance().apply {
            time = formatter.parse(initDate)!!
            add(Calendar.HOUR_OF_DAY, 4)
        }

        val targetDate: Long = targetCalendar.timeInMillis
        val currentTime = System.currentTimeMillis()
        val diff = targetDate - currentTime
        diffTime.longValue = diff

        if (diff > 0) {
            startTimer(context)
        } else {
            timerText.value = getStringResource(context, R.string.tap_to_mine)
            countDownTimer?.cancel()
        }
    }

    private fun startTimer(context: Context) {
        viewModelScope.launch{
            countDownTimer?.cancel()
            countDownTimer = object : CountDownTimer(diffTime.longValue, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerText.value = millisUntilFinished.timeFormat()
                }

                override fun onFinish() {
                    timerText.value = context.getString(R.string.tap_to_mine)
                }
            }
            countDownTimer?.start()
        }
    }
}


