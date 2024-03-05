package com.kaya.digitalmining.paymentService.paymentImplementor

interface BillingClientConnectionListener {
    fun onConnected(status: Boolean, billingResponseCode: Int)
}