package com.kaya.digitalmining.viewModel

import androidx.compose.runtime.mutableIntStateOf
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
    val oldMinerData = MutableLiveData<ArrayList<OldMiner>?>(ArrayList())
    private val firebaseImplementor = FirebaseImplementor()
    val totalSDKNetworkAmount = mutableIntStateOf(0)

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
                        }else {
                            minerData.value = null
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
        val tempList = ArrayList<OldMiner>()
        with(firebaseImplementor){
            firebaseUser?.let { fbUser->
                createOrOpenFirebaseDB("OldMiner")
                val minerListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            for(dataSnapshot in snapshot.children){
                                val oldMiner = OldMiner(
                                    dataSnapshot.child("minerID").value.toString(),
                                    dataSnapshot.child("userID").value.toString(),
                                    dataSnapshot.child("initDate").value.toString(),
                                    dataSnapshot.child("sdkCoinAmount").value.toString().toInt()
                                )
                                if(fbUser.uid == oldMiner.userID) { tempList.add(oldMiner) }
                                println(tempList)
                            }
                            oldMinerData.value = tempList
                        } else {
                            oldMinerData.value = null
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        oldMinerData.value = null
                    }
                }
                databaseReference?.child(fbUser.uid)!!.addValueEventListener(minerListener)
            }
        }
    }


    override fun getTotalSdkNetworkAmount() {
        with(firebaseImplementor){
            firebaseUser?.let {
                createOrOpenFirebaseDB("OldMiner")
                val totalListener = object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            var tempTotal = 0
                            for (dataSnapshot in snapshot.children){
                                val oldMiner = OldMiner(
                                    dataSnapshot.child("minerID").value.toString(),
                                    dataSnapshot.child("userID").value.toString(),
                                    dataSnapshot.child("initDate").value.toString(),
                                    dataSnapshot.child("sdkCoinAmount").value.toString().toInt()
                                )
                                tempTotal += oldMiner.sdkCoinAmount
                            }
                            totalSDKNetworkAmount.intValue = tempTotal
                        } else {
                            totalSDKNetworkAmount.intValue = 0
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        totalSDKNetworkAmount.intValue = 0
                    }
                }
                databaseReference?.child(firebaseUser!!.uid)?.addValueEventListener(totalListener)
            }
        }
    }

}