package com.kaya.digitalmining.contracts

import com.kaya.digitalmining.model.Miner


interface MinerImplementation {

    fun getMinerData(callback: (Miner?) -> Unit)

    fun setMinerData(miner : Miner, callback: (Boolean) -> Unit)

    fun setOldMinerData(miner : Miner, callback: (Boolean) -> Unit)

    fun getOldMinerData(callback: (List<Miner>?) -> Unit)
}