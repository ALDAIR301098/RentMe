package com.softgames.rentme.services

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthService {
    companion object {

        suspend fun getCurrentUser() = withContext(Dispatchers.IO) {
            Firebase.auth.currentUser
        }

        suspend fun getUserInfo(userId: String) = withContext(Dispatchers.IO) {
            Firebase.firestore.collection("Users").document(userId).get().await()
        }

    }
}