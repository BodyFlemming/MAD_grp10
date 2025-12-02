package com.example.mymons.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.mymons.ui.theme.Black
import com.example.mymons.ui.theme.White

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(onMenuClicked: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text("My Mons") },
        navigationIcon = {
            IconButton(onClick = {
                onMenuClicked()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Black,
            titleContentColor = White,
            navigationIconContentColor = White
        )
    )
}