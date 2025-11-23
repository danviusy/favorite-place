package com.example.favorittsted.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.favorittsted.data.FavorittSted
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

@Composable
fun ListScreen(modifier: Modifier = Modifier, navController: NavHostController, viewModel: PlaceViewModel) {
    val favoritePlaces by viewModel.favoritePlaces.collectAsState()


    Column (
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Navbar
            Toolbar (
                onBackClick = {
                    // Kart-visning
                    navController.navigate("map")
                }
            )
        }


        Column (
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(
                text = "Favoritt steder",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp,
                modifier = Modifier
                    .padding(vertical = 12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                // Lager et kort om hvert sted
                items(favoritePlaces) { favoritePlace ->
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .clickable {
                                viewModel.updateStateValues(
                                    favoritePlace.id,
                                    favoritePlace.name,
                                    favoritePlace.description,
                                    favoritePlace.address,
                                    favoritePlace.latitude,
                                    favoritePlace.longitude
                                )
                                navController.navigate("info")
                            },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column (
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = favoritePlace.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = favoritePlace.description,
                                fontSize = 16.sp,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = favoritePlace.address,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }

                    }
                }
            }

        }






    }
}