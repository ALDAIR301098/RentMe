package com.softgames.rentme.domain.model

import android.net.Uri
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.softgames.rentme.domain.model.RentMeUser.*

data class House(
    @get: Exclude var id: String = "",
    var name: String = "",
    var hostId: String = "",
    var hostName: String = "",
    var hostPhoto: String? = null,
    var colony: String = "",
    var description: String = "",
    var price: Float = 0.0f,
    var guestNumber: Int = 0,
    var rooms: Int = 0,
    var beds: Int = 0,
    var bathrooms: Int = 0,
    var photoList: List<Uri> = emptyList(),
    var rating: Float = 0.0f,
    var timesRated: Int = 0,
)

fun DocumentSnapshot.toHouse() =
    House(
        id = id,
        name = getString("name")!!,
        hostId = getString("hostId")!!,
        hostName = getString("hostName")!!,
        hostPhoto = getString("hostPhoto"),
        colony = getString("colony")!!,
        description = getString("description")!!,
        price = getDouble("price")!!.toFloat(),
        guestNumber = getLong("guestNumber")!!.toInt(),
        rooms = getLong("rooms")!!.toInt(),
        beds = getLong("beds")!!.toInt(),
        bathrooms = getLong("bathrooms")!!.toInt(),
        photoList = get("photoList") as List<Uri>,
        rating = getDouble("rating")!!.toFloat(),
        timesRated = getLong("timesRated")!!.toInt(),
    )
