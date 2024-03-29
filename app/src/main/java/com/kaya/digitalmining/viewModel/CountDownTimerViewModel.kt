package com.kaya.digitalmining.viewModel

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kaya.digitalmining.R
import com.kaya.digitalmining.util.TimeFormatExt.timeFormat
import java.text.SimpleDateFormat
import java.util.*

class CountDownTimerViewModel(context: Context) : ViewModel() {

    private var timerHandler: Handler = Handler(Looper.getMainLooper())
    private var timerRunnable: Runnable? = null

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val diffTime = mutableLongStateOf(0L)
    var timerText = mutableStateOf(getStringResource(context,R.string.synchronization___))

    fun startCountDownTimer(context: Context, initDate: String?) {
        initDate?.let {
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
                timerText.value = getStringResource(context,R.string.tap_to_mine)
            }
        }
    }

    private fun startTimer(context: Context) {
        timerRunnable = object : Runnable {
            override fun run() {
                val currentTimeLeft = diffTime.longValue - 1000
                if (currentTimeLeft > 0) {
                    timerText.value = currentTimeLeft.timeFormat()
                    diffTime.longValue = currentTimeLeft
                    timerHandler.postDelayed(this, 1000)
                } else {
                    timerText.value = getStringResource(context,R.string.tap_to_mine)
                }
            }
        }
        timerHandler.post(timerRunnable!!)
    }

    override fun onCleared() {
        super.onCleared()
        timerHandler.removeCallbacksAndMessages(null)
    }

    private fun getStringResource(context: Context, stringResId: Int): String {
        return context.resources.getString(stringResId)
    }

}

