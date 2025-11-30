package com.example.mymons.ui.horse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mymons.models.Horse

@Composable
fun HorseItem(horse: Horse) {
    Column(Modifier.fillMaxWidth()) {
        Row {
            Text("Id")
            Text(horse.id)
        }
        Row {
            Text("Name")
            Text(horse.name)
        }
    }
}