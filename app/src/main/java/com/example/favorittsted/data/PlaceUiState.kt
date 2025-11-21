package com.example.favorittsted.data

data class PlaceUiState (
    val currentId: Int = 0,
    val currentName: String = "",
    val currentDescription: String = "",
    val currentAddress: String = "",
    val currentLatitude: Double = 0.0,
    val currentLongitude: Double = 0.0,
    val saveSuccess: Boolean = false
)
