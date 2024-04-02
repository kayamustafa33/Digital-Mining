package com.kaya.digitalmining.viewModel

import android.content.Context
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaya.digitalmining.R
import com.kaya.digitalmining.util.TimeFormatExt.timeFormat
import com.kaya.digitalmining.util.getStringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

class CountDownTimerViewModel : ViewModel(){

    private var countDownTimer: CountDownTimer? = null

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val diffTime = mutableLongStateOf(0L)
    var timerText = mutableStateOf("")
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


