package com.kaya.digitalmining.contracts

import com.kaya.digitalmining.model.User

interface AuthImplementation {

    fun registerUser(user : User, callback: (Boolean) -> Unit)
    fun authUser(user: User, callback: (Boolean) -> Unit)
    fun isLogin() : Boolean
    fun logout()
    fun deleteAccount(result: (Boolean) -> Unit)
    fun resetPassword(confirmEmail: String, result: (Boolean) -> Unit)

}