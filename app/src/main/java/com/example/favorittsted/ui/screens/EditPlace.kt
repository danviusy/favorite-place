package com.example.favorittsted.ui.screens

import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.favorittsted.ui.components.Dialog
import com.example.favorittsted.viewmodels.PlaceViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.MarkerState.Companion.invoke
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Locale


@Composable
fun EditPlace(modifier: Modifier = Modifier, navController: NavHostController, viewModel: PlaceViewModel) {
    val context = LocalContext.current


    val placeUiState = viewModel.uiState.collectAsState()
    val id = placeUiState.value.currentId
    var name by remember { mutableStateOf(placeUiState.value.currentName) }
    var description by remember { mutableStateOf(placeUiState.value.currentDescription) }

    var latitude by remember { mutableStateOf(placeUiState.value.currentLatitude) }
    var longitude by remember { mutableStateOf(placeUiState.value.currentLongitude) }
    var address by remember { mutableStateOf(placeUiState.value.currentAddress) }




    val markerState = remember { MarkerState(position = LatLng(latitude, longitude)) }


    LaunchedEffect(latitude, longitude) {
        markerState.position = LatLng(latitude, longitude)
    }

    LaunchedEffect(latitude, longitude) {
        if (latitude != 0.0 && longitude != 0.0) {
            address = getAddress(context, LatLng(latitude, longitude))
        }
    }


    val fieldsEmpty = name.isEmpty() || description.isEmpty() || address.isEmpty()
    var showErrorMessage by remember { mutableStateOf(false) }




    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                latitude,
                longitude),
            20f
        )
    }

    Column (
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



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
        Text(text = "Rediger stedet")
        OutlinedTextField( // Navn-felt
            value = name,
            onValueChange = { name = it },
            label = { Text("Navn") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        OutlinedTextField( // Beskrivelse-felt
            value = description,
            onValueChange = { description = it },
            label = { Text("Beskrivelse") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        OutlinedTextField( // Beskrivelse-felt
            value = address,
            onValueChange = { address = it },
            label = { Text("Addresse") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Button(
            onClick = { // Knapp som oppdaterer data i databasen
                if (fieldsEmpty) {
                    showErrorMessage = true
                } else {
                    viewModel.updatePlace(id, name, description, address, latitude, longitude)
                    showErrorMessage = false
                    navController.navigate("map")
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp)
        ) {
            Text("Lagre", fontSize = 20.sp)
        }

        if (showErrorMessage) { // Feilmelding hvis ikke alle felt er fylt, forhindrer lagring av null-verdier
            Text(
                "Alle feltene må fylles ut",
                color = Color.Red,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            GoogleMap(
                cameraPositionState = cameraPositionState,
                onMapClick = {
                    latitude = it.latitude
                    longitude = it.longitude
                }

            ) {
                Marker (
                    state = markerState
                )
            }
        }


    }
}

