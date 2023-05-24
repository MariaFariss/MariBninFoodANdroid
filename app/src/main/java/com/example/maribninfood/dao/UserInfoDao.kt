package com.example.maribninfood.dao

import com.example.maribninfood.model.UserClass
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.auth.User

import com.google.firebase.auth.FirebaseAuth

object UserInfoDao {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getCurrentUser(): UserClass? {
        val user = auth.currentUser
        return if (user != null) {
            UserClass(user.displayName, user.email)
        } else {
            null
        }
    }

    fun updatePassword(password: String, onResult: () -> Unit) {
        val user = auth.currentUser
        user?.updatePassword(password)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult()
            }
        }
    }

    fun updateUsername(username: String, onResult: () -> Unit) {
        val user = auth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()

        user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult()
            }
        }
    }

    fun updateEmail(email: String, onResult: () -> Unit) {
        val user = auth.currentUser
        user?.updateEmail(email)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult()
            }
        }
    }
}
