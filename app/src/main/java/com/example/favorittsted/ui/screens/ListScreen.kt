package com.example.favorittsted.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.favorittsted.data.FavorittSted
import com.example.favorittsted.ui.components.Dialog
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


        Text(text = "Favoritt steder")

        Button(
            onClick = {
                navController.navigate("map")
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp)
        ) {
            Text("Kartvisning", fontSize = 20.sp)
        }

        LazyColumn {
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
                    Text(text = favoritePlace.name)
                    Text(text = favoritePlace.description)
                    Text(text = favoritePlace.address)
                }
            }
        }
    }
}