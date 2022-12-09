package com.softgames.rentme.domain.model

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class RentMeUser(
    @get:Exclude var id: String = "",
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
