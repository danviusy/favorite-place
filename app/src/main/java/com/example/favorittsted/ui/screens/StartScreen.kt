package com.example.favorittsted.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.favorittsted.R

@Composable
fun StartScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    Column (
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image( // Logo
            painter = painterResource(id = R.drawable.favoritt_sted_icon),
            contentDescription = "Logo",
            modifier = modifier
                .size(120.dp)
                .padding(4.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button( // Navigerer videre til kartvisning
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

}