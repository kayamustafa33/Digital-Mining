package com.kaya.digitalmining.paymentService.serviceData

import android.app.Activity
import android.content.Context
import com.kaya.digitalmining.paymentService.paymentImplementor.BillingClientConnectionListener
import com.kaya.digitalmining.paymentService.paymentImplementor.PurchaseServiceListener
import com.kaya.digitalmining.paymentService.paymentImplementor.SubscriptionServiceListener
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(DelicateCoroutinesApi::class, DelicateCoroutinesApi::class)
class IapConnector @JvmOverloads constructor(
    context: Context,
    nonConsumableKeys: List<String> = emptyList(),
    consumableKeys: List<String> = emptyList(),
    subscriptionKeys: List<String> = emptyList(),
    key: String? = null,
    enableLogging: Boolean = false
) {

    private var mBillingService: IBillingService? = null

    init {
        val contextLocal = context.applicationContext ?: context
        mBillingService = BillingService(contextLocal, nonConsumableKeys, consumableKeys, subscriptionKeys)
        getBillingService().init(key)
        getBillingService().enableDebugLogging(enableLogging)
    }

    fun addBillingClientConnectionListener(billingClientConnectionListener: BillingClientConnectionListener) {
        getBillingService().addBillingClientConnectionListener(billingClientConnectionListener)
    }

    fun removeBillingClientConnectionListener(billingClientConnectionListener: BillingClientConnectionListener) {
        getBillingService().removeBillingClientConnectionListener(billingClientConnectionListener)
    }

    fun addPurchaseListener(purchaseServiceListener: PurchaseServiceListener) {
        getBillingService().addPurchaseListener(purchaseServiceListener)
    }

    fun removePurchaseListener(purchaseServiceListener: PurchaseServiceListener) {
        getBillingService().removePurchaseListener(purchaseServiceListener)
    }

    fun addSubscriptionListener(subscriptionServiceListener: SubscriptionServiceListener) {
        getBillingService().addSubscriptionListener(subscriptionServiceListener)
    }

    fun removeSubscriptionListener(subscriptionServiceListener: SubscriptionServiceListener) {
        getBillingService().removeSubscriptionListener(subscriptionServiceListener)
    }

    fun purchase(activity: Activity, sku: String, obfuscatedAccountId: String? = null, obfuscatedProfileId: String? = null) {
        getBillingService().buy(activity, sku, obfuscatedAccountId, obfuscatedProfileId)
    }

    fun subscribe(activity: Activity, sku: String, obfuscatedAccountId: String? = null, obfuscatedProfileId: String? = null) {
        getBillingService().subscribe(activity, sku, obfuscatedAccountId, obfuscatedProfileId)
    }

    fun unsubscribe(activity: Activity, sku: String) {
        getBillingService().unsubscribe(activity, sku)
    }

    fun destroy() {
        getBillingService().close()
    }

    private fun getBillingService(): IBillingService {
        return mBillingService ?: let {
            throw RuntimeException("Call IapConnector to initialize billing service")
        }
    }
}