package com.kaya.digitalmining.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kaya.digitalmining.contracts.MinerImplementation
import com.kaya.digitalmining.model.Miner
import com.kaya.digitalmining.model.OldMiner
import com.kaya.digitalmining.service.FirebaseImplementor

class MinerViewModel : ViewModel(), MinerImplementation {

    val minerData = MutableLiveData<Miner?>()
    val oldMinerData = MutableLiveData<List<OldMiner>>()
    private val firebaseImplementor = FirebaseImplementor()

    override fun getMinerData() {
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
                            minerData.value = miner
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        minerData.value = null
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

    override fun setOldMinerData(miner: Miner) {
        with(firebaseImplementor){
            firebaseUser?.let {
                createOrOpenFirebaseDB("OldMiner")
                databaseReference?.child(miner.userID)?.child(miner.minerID)
                    ?.setValue(miner)
            }
        }
    }

    override fun getOldMinerData() {
        with(firebaseImplementor){
            firebaseUser?.let {
                createOrOpenFirebaseDB("OldMiner")
                val minerListener = object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            val tempList = mutableListOf<OldMiner>()
                            for(dataSnapshot in snapshot.children){
                                val miner = OldMiner(
                                    snapshot.child("minerID").value.toString(),
                                    snapshot.child("userID").value.toString(),
                                    snapshot.child("initDate").value.toString(),
                                    snapshot.child("sdkCoinAmount").value.toString().toInt()
                                )
                                tempList.add(miner)
                            }
                            oldMinerData.value = tempList
                            tempList.clear()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                }
                databaseReference?.child(firebaseUser!!.uid)!!.addListenerForSingleValueEvent(minerListener)
            }

        }
    }

}