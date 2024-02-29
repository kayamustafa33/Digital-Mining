package com.kaya.digitalmining.controller

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import java.security.SecureRandom

class HashController {


    companion object {
        const val BASE58_ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    }

    private val random = SecureRandom()

    fun hashCoinText(diff: Long,callback: (String) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            if (diff <= 0) return@launch

            callback(randomBase58())
            yield()
            delay(100)
            hashCoinText(diff - 100,callback)
        }
    }

    private fun randomBase58(): String {
        return (1..25)
            .map { BASE58_ALPHABET[random.nextInt(BASE58_ALPHABET.length)] }
            .joinToString("")
    }
}