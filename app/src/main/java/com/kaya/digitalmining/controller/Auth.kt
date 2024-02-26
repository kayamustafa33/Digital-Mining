package com.kaya.digitalmining.controller

import com.kaya.digitalmining.contracts.AuthImplementation
import com.kaya.digitalmining.model.User
import com.kaya.digitalmining.service.FirebaseImplementor

class Auth : AuthImplementation {

    private val initFirebase = FirebaseImplementor()
    override fun registerUser(user: User, callback: (Boolean) -> Unit) {
        with(initFirebase){
            firebaseAuth?.createUserWithEmailAndPassword(user.userEmail,user.userPassword)
                ?.addOnCompleteListener {
                    if(it.isSuccessful){
                        callback(true)
                    }else {
                        callback(false)
                    }
                }?.addOnFailureListener {
                    callback(false)
                }

        }
    }

    override fun authUser(user: User,callback: (Boolean) -> Unit) {
        with(initFirebase){
            firebaseAuth?.signInWithEmailAndPassword(user.userEmail,user.userPassword)
                ?.addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        callback(true)
                    }else {
                        callback(false)
                    }
                }?.addOnFailureListener {
                    callback(false)
                }
        }
    }

    override fun isLogin() : Boolean {
        with(initFirebase){
            return firebaseAuth?.currentUser != null
        }
    }
}