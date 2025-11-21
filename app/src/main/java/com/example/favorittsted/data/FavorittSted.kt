package com.example.favorittsted.data

import com.squareup.moshi.Json

data class FavorittSted(
    @Json(name = "id") val id: Int,
    @Json(name = "Name") val name: String,
    @Json(name = "Description") val description: String,
    @Json(name = "Address") val address: String,
    @Json(name = "Latitude") val latitude: Double,
    @Json(name = "Longitude") val longitude: Double
)

