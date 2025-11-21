package com.example.favorittsted

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.favorittsted.ui.navigation.NavigationGraph
import com.example.favorittsted.ui.screens.MapScreen
import com.example.favorittsted.ui.theme.FavorittStedTheme
import com.example.favorittsted.viewmodels.PlaceViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.compose.GoogleMap

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FavorittStedTheme {
                Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FavoritePlace(modifier = Modifier.padding(innerPadding), viewModel = PlaceViewModel())
                }
            }
        }
    }
}

@Composable
fun FavoritePlace(modifier: Modifier = Modifier, viewModel: PlaceViewModel) {
    val navController = rememberNavController()
    NavigationGraph(modifier = modifier, navController = navController, viewModel = viewModel)
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FavorittStedTheme {
        Greeting("Android")
    }
}