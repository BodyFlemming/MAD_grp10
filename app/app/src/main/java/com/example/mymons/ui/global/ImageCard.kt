package com.example.mymons.ui.global

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymons.ui.theme.Black
import com.example.mymons.ui.theme.White

@Composable
fun ImageCard(
    imageUrl: String,
    titleContent: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(80.dp),
            shape = RoundedCornerShape(12.dp),
            color = Black,
            border = BorderStroke(2.dp, White)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Profile image",
                Modifier.size(60.dp)
            )

        }


        Spacer(modifier = Modifier.width(28.dp))

        titleContent()
    }
}