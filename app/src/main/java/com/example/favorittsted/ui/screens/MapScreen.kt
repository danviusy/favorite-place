package com.example.favorittsted.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.navigation.NavHostController
import com.example.favorittsted.data.FavorittSted
import com.example.favorittsted.ui.components.Dialog
import com.example.favorittsted.viewmodels.PlaceViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(modifier: Modifier = Modifier, navController: NavHostController, viewModel: PlaceViewModel) {
    val favoritePlaces by viewModel.favoritePlaces.collectAsState()
    var selectedPlace by remember { mutableStateOf<FavorittSted?>(null) }

    var currentLatitude by remember { mutableStateOf(0.0) }
    var currentLongitude by remember { mutableStateOf(0.0) }

    var showDialog by remember { mutableStateOf(false) }

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

        if (showDialog) {
            Dialog(
                onDismissRequest = {showDialog = false},
                onConfirmation = {
                    showDialog = false
                    viewModel.onCreationOfPlace(currentLatitude, currentLongitude)
                    navController.navigate("add") },
                dialogTitle = "Vil du legge til et nytt sted?",
                dialogText = "Du kan alltid lagre nye steder senere"
            )

        }

        Text(text = "Favoritt steder")


        GoogleMap(
            cameraPositionState = cameraPositionState,
            onMapClick = {

                currentLatitude = it.latitude
                currentLongitude = it.longitude
                showDialog = true;
            }

        ) {
            favoritePlaces.forEach { favoritePlace ->
                Marker (
                    state = MarkerState(position = LatLng(favoritePlace.latitude, favoritePlace.longitude)),
                    title = favoritePlace.name,
                    snippet = favoritePlace.description,
                    onClick = {
                        viewModel.updateStateValues(
                            favoritePlace.name,
                            favoritePlace.description,
                            favoritePlace.address,
                            favoritePlace.latitude,
                            favoritePlace.longitude
                        )
                        navController.navigate("info")
                        true
                    }
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth()) {

        }


    }
}