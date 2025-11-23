package com.example.favorittsted.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.favorittsted.ui.components.Dialog
import com.example.favorittsted.ui.components.Toolbar
import com.example.favorittsted.viewmodels.PlaceViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(modifier: Modifier = Modifier, navController: NavHostController, viewModel: PlaceViewModel) {


    val favoritePlaces by viewModel.favoritePlaces.collectAsState() // Hentet liste av lagrede steder

    var currentLatitude by remember { mutableStateOf(0.0) }
    var currentLongitude by remember { mutableStateOf(0.0) }

    var showDialog by remember { mutableStateOf(false) }

    // Kameraposisjon
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                59.912766,
                10.746189),
            10f
        )
    }
    Column (modifier = modifier.fillMaxSize()) {
        // Nav-bar
        Toolbar (
            onBackClick = {
                navController.navigate("start")
            },
            extraActions = listOf(
                {
                    IconButton(onClick = {
                        // Liste-visning
                        navController.navigate("list")
                    }) {
                        Icon(Icons.Default.List, contentDescription = "List-view")
                    }
                }
            )
        )
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
                        // Oppdaterer UI-state variablene med nye verdiene
                        viewModel.onCreationOfPlace(currentLatitude, currentLongitude)
                        // Navigerer til AddPlace-skjermen
                        navController.navigate("add") },
                    dialogTitle = "Vil du legge til et nytt sted?",
                    dialogText = "Du kan alltid lagre nye steder senere"
                )

            }



            Text(
                text = "Favoritt steder",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp,
                modifier = Modifier
                    .padding(vertical = 12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .padding(8.dp)
            ) {
                GoogleMap(
                    cameraPositionState = cameraPositionState,
                    onMapClick = {
                        currentLatitude = it.latitude
                        currentLongitude = it.longitude
                        showDialog = true;
                    },
                    modifier = Modifier.fillMaxSize()

                ) {
                    // Marker for hver lagret sted
                    favoritePlaces.forEach { favoritePlace ->
                        Marker (
                            state = MarkerState(position = LatLng(favoritePlace.latitude, favoritePlace.longitude)),
                            title = favoritePlace.name,
                            snippet = favoritePlace.description,
                            onClick = {
                                viewModel.updateStateValues(
                                    favoritePlace.id,
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
            }

        }
    }

}