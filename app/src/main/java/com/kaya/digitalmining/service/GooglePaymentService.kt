package com.kaya.digitalmining.service

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaya.digitalmining.paymentService.paymentImplementor.BillingClientConnectionListener
import com.kaya.digitalmining.paymentService.paymentImplementor.PurchaseServiceListener
import com.kaya.digitalmining.paymentService.serviceData.DataWrappers
import com.kaya.digitalmining.paymentService.serviceData.IapConnector

class GooglePaymentService : BillingClientConnectionListener, ViewModel(){

    private val isBillingConnected = MutableLiveData<Boolean>()
    val isPurchased = MutableLiveData<Boolean>()

    fun connectPayment(productList: MutableList<String>,context : Context) : IapConnector {
        isBillingConnected.value = false
        val iapConnector = IapConnector(
            context = context,
            nonConsumableKeys = listOf("lifetime"), // pass the list of non-consumables
            consumableKeys = productList, // pass the list of consumables
            subscriptionKeys = listOf("subscription"), // pass the list of subscriptions
            key = "Enter google console payment key",
            enableLogging = true
        )

        iapConnector.addBillingClientConnectionListener(this)

        iapConnector.addPurchaseListener(object : PurchaseServiceListener {

            override fun onPricesUpdated(iapKeyPrices: Map<String, List<DataWrappers.ProductDetails>>) {
                //empty
            }

            // Add product list item according to your subs size.
            override fun onProductPurchased(purchaseInfo: DataWrappers.PurchaseInfo) {
                when (purchaseInfo.sku) {
                    productList[0] -> {
                        isPurchased.value = true
                    }
                    productList[1] -> {
                        isPurchased.value = true
                    }
                    productList[2] -> {
                        isPurchased.value = true
                    }
                    productList[3] -> {
                        isPurchased.value = true
                    }
                    productList[4] -> {
                        isPurchased.value = true
                    }
                    productList[5] -> {
                        isPurchased.value = true
                    }
                }
            }

            override fun onProductRestored(purchaseInfo: DataWrappers.PurchaseInfo) {

            }
        })

        return iapConnector
    }

    override fun onConnected(status: Boolean, billingResponseCode: Int) {
        isBillingConnected.value = status
    }

}