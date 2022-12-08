package com.softgames.rentme.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class RentMeUser(
    var name: String = "",
    var lastName: String = "",
    var age: Int = 0,
    var gender: String = "",
    var type: String = "",
    var photo: Uri? = null,
) : Parcelable {
    object Host : RentMeUser()
    object Guest : RentMeUser()
}
