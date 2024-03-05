package com.kaya.digitalmining.paymentService.paymentImplementor

import com.kaya.digitalmining.paymentService.serviceData.DataWrappers

interface PurchaseServiceListener : BillingServiceListener {

    override fun onPricesUpdated(iapKeyPrices: Map<String, List<DataWrappers.ProductDetails>>)

    fun onProductPurchased(purchaseInfo: DataWrappers.PurchaseInfo)

    fun onProductRestored(purchaseInfo: DataWrappers.PurchaseInfo)
}