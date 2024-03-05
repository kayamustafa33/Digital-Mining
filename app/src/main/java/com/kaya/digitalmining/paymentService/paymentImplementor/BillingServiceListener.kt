package com.kaya.digitalmining.paymentService.paymentImplementor

import com.kaya.digitalmining.paymentService.serviceData.DataWrappers

interface BillingServiceListener {

    fun onPricesUpdated(iapKeyPrices: Map<String, List<DataWrappers.ProductDetails>>)
}