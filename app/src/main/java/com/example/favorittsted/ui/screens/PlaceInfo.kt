package com.example.favorittsted.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.favorittsted.viewmodels.PlaceViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun PlaceInfo(modifier: Modifier = Modifier, navController: NavHostController, viewModel: PlaceViewModel) {
    val placeUiState = viewModel.uiState.collectAsState()
    val name = placeUiState.value.currentName
    val description = placeUiState.value.currentDescription
    val address = placeUiState.value.currentAddress
    val longitude = placeUiState.value.currentLongitude
    val latitude = placeUiState.value.currentLatitude

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                latitude,
                longitude),
            15f
        )
    }

    Column (
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(name)
        Text(description)
        Text(address)
        Text(longitude.toString())
        Text(latitude.toString())

        Button(
            onClick = {
                navController.navigate("map")
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp)
        ) {
            Text("Gå tilbake", fontSize = 20.sp)
        }
        Button(
            onClick = {
                navController.navigate("edit")
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp)
        ) {
            Text("Rediger", fontSize = 20.sp)
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            GoogleMap(
                cameraPositionState = cameraPositionState

            ) {
                Marker (
                    state = rememberMarkerState(position = LatLng(latitude, longitude))
                )
            }
        }



    }




}