package com.kaya.digitalmining.controller

import android.util.Log
import com.kaya.digitalmining.contracts.AuthImplementation
import com.kaya.digitalmining.model.User
import com.kaya.digitalmining.service.FirebaseImplementor

class Auth : AuthImplementation {

    private val initFirebase = FirebaseImplementor()

    override fun registerUser(user: User, callback: (Boolean) -> Unit) {
        with(initFirebase) {
            firebaseAuth?.createUserWithEmailAndPassword(user.userEmail, user.userPassword)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUser = firebaseAuth!!.currentUser
                        currentUser?.let {
                            registerUserToRealTimeDB(user)
                            callback(true)
                        }
                    } else {
                        callback(false)
                    }
                }?.addOnFailureListener {
                    callback(false)
                }
        }
    }

    override fun authUser(user: User, callback: (Boolean) -> Unit) {
        with(initFirebase) {
            firebaseAuth?.signInWithEmailAndPassword(user.userEmail, user.userPassword)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback(true)
                    } else {
                        callback(false)
                    }
                }?.addOnFailureListener {
                    callback(false)
                }
        }
    }

    override fun isLogin(): Boolean = with(initFirebase) {
        return firebaseAuth?.currentUser != null

    }

    override fun logout() {
        initFirebase.firebaseAuth!!.signOut()
    }

    override fun deleteAccount(result: (Boolean) -> Unit) {
        val user = initFirebase.firebaseAuth!!.currentUser!!
        user.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) result(true)
            else result(false)
        }.addOnFailureListener {
            result(false)
        }
    }

    override fun resetPassword(confirmEmail: String, result: (Boolean) -> Unit): Unit = with(initFirebase) {
        firebaseAuth!!.sendPasswordResetEmail(confirmEmail).addOnCompleteListener { task ->
            if (task.isSuccessful) result(true)
            else result(false)
        }.addOnFailureListener {
            result(false)
        }
    }

    private fun registerUserToRealTimeDB(user: User) {
        val firebaseUser = initFirebase.firebaseAuth?.currentUser
        if (firebaseUser != null) {
            with(initFirebase) {
                createOrOpenFirebaseDB("User")
                val userId = firebaseUser.uid
                databaseReference!!.child(userId).setValue(user)
            }
        }
    }
}