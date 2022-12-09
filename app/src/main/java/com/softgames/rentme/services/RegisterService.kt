package com.softgames.rentme.services

import android.net.Uri
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.softgames.rentme.domain.model.RentMeUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegisterService {
    companion object {

        suspend fun checkIfUserExist(userId: String) = withContext(Dispatchers.IO) {
            Firebase.firestore.collection("Users").document(userId).get().await().exists()
        }

        suspend fun registerUser(user: RentMeUser): Unit = withContext(Dispatchers.IO) {
            Firebase.firestore.collection("Users").document(user.id).set(user).await()
        }
    }
}