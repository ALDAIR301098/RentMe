package com.softgames.rentme.domain.model

import android.net.Uri
import com.softgames.rentme.domain.model.RentMeUser.*

data class House(
    var name: String = "",
    var host: Host = Host,
    var colony: String = "",
    var description: String = "",
    var price: Float = 0.0f,
    var guestNumber: Int = 0,
    var rooms: Int = 0,
    var beds: Int = 0,
    var bathrooms: Int = 0,
    var photoList: List<Uri> = emptyList(),
    var rating: Float = 0.0f,
    var timesRated: Int = 0
)
