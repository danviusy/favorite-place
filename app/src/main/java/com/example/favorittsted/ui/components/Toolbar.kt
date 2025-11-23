package com.example.favorittsted.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


// Nav-bar (Toolbar) for enkel navigering og gjenbrukbar på flere skjermbilder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    onBackClick: () -> Unit,
    extraActions: List<@Composable () -> Unit> = emptyList()
) {

    TopAppBar(
        modifier = Modifier.height(80.dp),
        title = {  },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            extraActions.forEach { extraAction ->
                extraAction()
            }
        }

    )
}