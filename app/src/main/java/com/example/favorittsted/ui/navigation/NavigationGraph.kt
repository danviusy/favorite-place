package com.example.favorittsted.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.favorittsted.ui.screens.AddPlace
import com.example.favorittsted.ui.screens.MapScreen
import com.example.favorittsted.ui.screens.PlaceInfo
import com.example.favorittsted.viewmodels.PlaceViewModel

@Composable
fun NavigationGraph(modifier: Modifier = Modifier, navController: NavHostController, viewModel: PlaceViewModel) {
    NavHost(navController = navController, startDestination = "start") {
        composable("start") { MapScreen(modifier = modifier, navController = navController, viewModel = viewModel) }
        composable("add") { AddPlace(modifier = modifier, navController = navController, viewModel = viewModel) }
        composable("info") { PlaceInfo(modifier = modifier, navController = navController, viewModel = viewModel) }
    }
}