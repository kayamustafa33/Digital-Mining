package com.kaya.digitalmining.model

import java.util.Date

data class Purchase(
    val miningRateId: String,
    val price: Double,
    val userEmail: String,
    val purchaseDate: Date,
    val membershipExpirationDate: Date
)