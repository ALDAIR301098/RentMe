package com.softgames.rentme.services

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class StorageService {
    companion object {
        suspend fun uploadUserPhoto(userId: String, imageUri: Uri) = withContext(Dispatchers.IO) {
            Firebase.storage.reference.child("Users").child(userId)
                .child("User profile picture").putFile(imageUri)
                .await().storage.downloadUrl.await().toString()
        }

        suspend fun uploadHousePhotos(userId: String, houseId: String, imageUris: List<Uri>) =
            withContext(Dispatchers.IO) {
                val imageUrls = ArrayList<String>()
                imageUris.forEach { uri ->
                    imageUrls.add(
                        Firebase.storage.reference.child("Houses").child(userId)
                            .child(houseId).child(UUID.randomUUID().toString()).putFile(uri)
                            .await().storage.downloadUrl.await().toString()
                    )
                }
                imageUrls
            }
    }
}