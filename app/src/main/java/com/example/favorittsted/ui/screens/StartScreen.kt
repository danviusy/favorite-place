package com.example.favorittsted.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.favorittsted.viewmodels.PlaceViewModel

@Composable
fun StartScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Button(
        onClick = {
            navController.navigate("map")
        },
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(56.dp)
    ) {
        Text("Se lagret steder", fontSize = 20.sp)
    }
}