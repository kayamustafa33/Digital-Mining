package com.kaya.digitalmining.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kaya.digitalmining.contracts.MinerImplementation
import com.kaya.digitalmining.model.Miner
import com.kaya.digitalmining.service.FirebaseImplementor

class MinerViewModel : ViewModel(), MinerImplementation {

    val minerData = MutableLiveData<Miner>()
    private val firebaseImplementor = FirebaseImplementor()

    override fun getMinerData(callback: (Miner?) -> Unit) {
        with(firebaseImplementor){
            firebaseUser?.let {
                createOrOpenFirebaseDB("Miner")
                val minerListener = object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            val miner = Miner(
                                snapshot.child("minerID").value.toString(),
                                snapshot.child("userID").value.toString(),
                                snapshot.child("initDate").value.toString(),
                                snapshot.child("sdkCoinAmount").value.toString().toInt()
                            )
                            callback(miner)
                            minerData.value = miner
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        callback(null)
                    }

                }
                databaseReference?.child(firebaseUser!!.uid)!!.addListenerForSingleValueEvent(minerListener)
            }

        }
    }

    override fun setMinerData(miner: Miner, callback: (Boolean) -> Unit) {
        with(firebaseImplementor){
            firebaseUser?.let {
                createOrOpenFirebaseDB("Miner")
                databaseReference?.child(miner.userID)
                    ?.setValue(miner)
                    ?.addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            callback(true)
                        }else{
                            callback(false)
                        }
                    }?.addOnFailureListener {
                        callback(false)
                    }
            }

        }
    }

    override fun setOldMinerData(miner: Miner, callback: (Boolean) -> Unit) {

    }

    override fun getOldMinerData(callback: (List<Miner>?) -> Unit) {

    }

}