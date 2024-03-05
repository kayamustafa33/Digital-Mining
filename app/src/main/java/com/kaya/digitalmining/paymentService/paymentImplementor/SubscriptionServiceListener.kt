package com.kaya.digitalmining.paymentService.paymentImplementor

import com.kaya.digitalmining.paymentService.serviceData.DataWrappers

interface SubscriptionServiceListener : BillingServiceListener {

    fun onSubscriptionRestored(purchaseInfo: DataWrappers.PurchaseInfo)

    fun onSubscriptionPurchased(purchaseInfo: DataWrappers.PurchaseInfo)
}