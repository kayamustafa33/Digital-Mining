package com.kaya.digitalmining.controller

import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class DateController {

    private var countDownTimer: CountDownTimer? = null
    private val hashController = HashController()

    fun countDownTimer(initDate: String, remainText: TextView, hashText: TextView) {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val targetDate = Calendar.getInstance()
        targetDate.time = formatter.parse(initDate)!!
        targetDate.add(Calendar.HOUR_OF_DAY,4)

        val currentDate = Calendar.getInstance().time
        val diff = targetDate.timeInMillis - currentDate.time

        if (diff > 0) {
            hashController.hashCoinText(hashText,diff)

            countDownTimer = object : CountDownTimer(diff, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val seconds = millisUntilFinished / 1000
                    val minutes = seconds / 60
                    val hours = minutes / 60
                    val remainingMinutes = minutes % 60
                    val remainingSeconds = seconds % 60
                    remainText.text = String.format("%02d:%02d:%02d", hours, remainingMinutes, remainingSeconds)
                }

                override fun onFinish() {
                    remainText.text = "Countdown finished!"
                    stopCountdown()
                    hashText.visibility = View.INVISIBLE
                }
            }
            countDownTimer?.start()
        } else {
            remainText.text = "Countdown finished!"
            stopCountdown()
            hashText.visibility = View.INVISIBLE
        }
    }

    fun stopCountdown() {
        countDownTimer?.cancel()
    }

}