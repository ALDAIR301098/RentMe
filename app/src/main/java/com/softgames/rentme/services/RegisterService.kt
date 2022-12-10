package com.softgames.rentme.services

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.softgames.rentme.domain.model.House
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
            Firebase.firestore.collection("Users").document(user.uid).set(user).await()
        }

        suspend fun registerHouse(house: House): DocumentReference = withContext(Dispatchers.IO) {
            Firebase.firestore.collection("Houses").add(house).await()
        }

        suspend fun uploadHouseImagesUrls(houseId: String, imageUrls: List<String>): Unit =
            withContext(Dispatchers.IO) {
                Firebase.firestore.collection("Houses").document(houseId)
                    .update("photoList", imageUrls).await()
            }

    }
}