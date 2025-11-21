package com.example.favorittsted.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.favorittsted.viewmodels.PlaceViewModel

@Composable
fun MapScreen(viewModel: PlaceViewModel = viewModel()) {
    val favoritePlaces by viewModel.favoritePlaces.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Favoritt steder")
        favoritePlaces.forEach { favoritePlace ->
            Text(text = favoritePlace.name)
            Text(text = favoritePlace.latitude.toString())
            Text(text = favoritePlace.longitude.toString())
        }
    }
}