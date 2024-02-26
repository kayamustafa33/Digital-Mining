package com.kaya.digitalmining.controller

import android.widget.TextView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.util.Timer
import java.util.TimerTask

class HashController {


    companion object {
        const val BASE58_ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    }
    fun hashCoinText(hashText: TextView, diff: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            hashCoinTextHelper(hashText, diff)
        }
    }

    private suspend fun hashCoinTextHelper(hashText: TextView, diff: Long) {
        if (diff <= 0) return

        hashText.text = randomBase58()
        delay(100)
        hashCoinTextHelper(hashText, diff - 100)
    }

    private fun randomBase58(): String {
        val random = SecureRandom()
        return (1..25)
            .map { BASE58_ALPHABET[random.nextInt(BASE58_ALPHABET.length)] }
            .joinToString("")
    }

}