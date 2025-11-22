package com.example.favorittsted.ui.screens

import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.favorittsted.ui.components.Dialog
import com.example.favorittsted.ui.components.Toolbar

import com.example.favorittsted.viewmodels.PlaceViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.MarkerState.Companion.invoke
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import java.util.Locale

@Composable
fun AddPlace(modifier: Modifier = Modifier, navController: NavHostController, viewModel: PlaceViewModel) {
    val context = LocalContext.current


    val placeUiState = viewModel.uiState.collectAsState()
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var latitude by remember { mutableStateOf(placeUiState.value.currentLatitude) }
    var longitude by remember { mutableStateOf(placeUiState.value.currentLongitude) }
    var address by remember { mutableStateOf("") }


    var showDialog by remember { mutableStateOf(false) }
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
        Toolbar (
            onBackClick = {
                showDialog = true
            },
            extraActions = listOf(
                {
                    IconButton(onClick = {
                        if (fieldsEmpty) {
                            showErrorMessage = true
                        } else {
                            viewModel.putFavoritePlace(name, description, address, latitude, longitude)
                            showErrorMessage = false
                            navController.navigate("map")
                        }
                    }) {
                        Icon(Icons.Default.Save, contentDescription = "Save")
                    }
                }
            )
        )

        if (showDialog) {
            Dialog(
                onDismissRequest = {showDialog = false},
                onConfirmation = {
                    viewModel.resetStateValues()
                    navController.navigate("map") },
                dialogTitle = "Vil du gå tilbake?",
                dialogText = "Du mister endringene du har gjort."
            )
        }

        Text(
            text = "Lagre stedet",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp,
            modifier = Modifier
                .padding(vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
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


        if (showErrorMessage) { // Feilmelding hvis ikke alle felt er fylt, forhindrer lagring av null-verdier
            Text(
                "Alle feltene må fylles ut",
                color = Color.Red,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(8.dp)
        ) {
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

fun getAddress(context: Context, latLng: LatLng): String {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val result = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        if (!result.isNullOrEmpty()) {
            result[0].getAddressLine(0) ?: ""
        } else {
            ""
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}