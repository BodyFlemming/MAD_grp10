package com.example.mymons.ui.global

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymons.ui.theme.White

@Composable
fun BulletList(items: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        items.forEach { item ->
            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = "\u2022",
                    modifier = Modifier.padding(end = 8.dp),
                    fontSize = 20.sp,
                    color = White
                )
                Text(text = item, fontSize = 20.sp, color = White)
            }
        }
    }
}