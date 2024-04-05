package com.kaya.digitalmining.service

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kaya.digitalmining.model.Purchase

class SubscriptionService {

    private val firebaseImplementor = FirebaseImplementor()
    private val googlePaymentService = GooglePaymentService()

    fun observePurchase(owner: LifecycleOwner) {
        googlePaymentService.isPurchased.observe(owner) { isPurchased ->
            if (isPurchased) {
                googlePaymentService.purchaseData.value?.let { savePurchaseData(it) }
            }
        }
    }

    private fun savePurchaseData(purchase: Purchase) = with(firebaseImplementor.databaseReference) {
        Log.d("purchase-log", purchase.miningRateId)
        /*this?.let {
            child(firebaseImplementor.firebaseAuth?.currentUser?.email.toString()).child("")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
        }*/
    }


}