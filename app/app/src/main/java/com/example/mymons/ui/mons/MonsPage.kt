package com.example.mymons.ui.mons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mymons.models.Mon

@Composable
fun Mons(mons: List<Mon>) {
    Column() {
        mons.forEach { MonItem(it) }
    }
}

@Composable
fun MonItem(mon: Mon) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row() {
            // Image
            Column {
                Text(mon.name)
                Text("Capture date")
            }
            // Arrow icon
        }

    }
}