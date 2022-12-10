package com.softgames.rentme.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.softgames.rentme.domain.model.RentMeUser.*
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class RentMeUser(
    @get:Exclude var uid: String = "",
    var name: String = "",
    var lastName: String = "",
    var birthDate: String = "",
    var gender: String = "",
    var type: String = "",
    var photo: String? = null,
) : Parcelable {
    object Host : RentMeUser(type = "Host")
    object Guest : RentMeUser(type = "Guest")
}

fun DocumentSnapshot.toHost() =
    Host.apply {
        uid = id; name = getString("name")!!;
        lastName = getString("lastName")!!; birthDate = getString("birthDate")!!
        gender = getString("gender")!!; type = getString("type")!!
        photo = getString("photo")
    }

fun DocumentSnapshot.toGuest() =
    Guest.apply {
        uid = id; name = getString("name")!!;
        lastName = getString("lastName")!!; birthDate = getString("birthDate")!!
        gender = getString("gender")!!; type = getString("type")!!
        photo = getString("photo")
    }
