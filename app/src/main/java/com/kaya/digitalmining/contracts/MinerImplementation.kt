package com.kaya.digitalmining.contracts

import com.kaya.digitalmining.model.Miner


interface MinerImplementation {

    fun getMinerData()

    fun setMinerData(miner : Miner, callback: (Boolean) -> Unit)

    fun setOldMinerData(miner : Miner)

    fun getOldMinerData()
}