package com.example.favorittsted.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.favorittsted.viewmodels.PlaceViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(modifier: Modifier = Modifier, viewModel: PlaceViewModel = viewModel()) {
    val favoritePlaces by viewModel.favoritePlaces.collectAsState()


    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                59.912766,
                10.746189),
            10f
        )
    }

    Column (
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Favoritt steder")


        Box(modifier = Modifier.fillMaxWidth()) {
            GoogleMap(
                cameraPositionState = cameraPositionState,
                onMapClick = { } // Henter LatLnge fra Google Maps

            ) {
                favoritePlaces.forEach { favoritePlaces ->
                    Marker (
                        state = MarkerState(position = LatLng(favoritePlaces.latitude, favoritePlaces.longitude)),
                        title = favoritePlaces.name,
                        snippet = favoritePlaces.description
                    )
                }
            }
        }

    }
}