package com.softgames.rentme.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.softgames.rentme.domain.model.House
import com.softgames.rentme.domain.model.toHouse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HousesRepo {
    companion object {

        suspend fun getHostHomesList(userId: String): MutableList<DocumentSnapshot> =
            withContext(Dispatchers.IO) {
                Firebase.firestore.collection("Houses")
                    .whereEqualTo("hostId", userId).get().await().documents
            }

        suspend fun getFullHomeList(): MutableList<DocumentSnapshot> = withContext(Dispatchers.IO) {
            Firebase.firestore.collection("Houses").get().await().documents
        }

        suspend fun getCurrentHouse(houseId: String): House = withContext(Dispatchers.IO) {
            Firebase.firestore.collection("Houses").document(houseId).get().await().toHouse()
        }

    }
}