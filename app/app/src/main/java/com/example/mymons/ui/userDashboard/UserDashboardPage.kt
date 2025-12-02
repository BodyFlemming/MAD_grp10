package com.example.mymons.ui.userDashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymons.ui.global.BulletList
import com.example.mymons.ui.global.ImageCard
import com.example.mymons.ui.theme.White

@Composable
fun UserDashboardPage() {
    val dummyUserName = "Benjamin"
    val dummyAmountOfMons = 22
    val dummyAmountOfShinies = 2

    Column(
        verticalArrangement = Arrangement.spacedBy(64.dp)
    ) {
        ImageCard("") {
            Text("User name", fontSize = 30.sp, color = White)
        }

        BulletList(
            listOf(
                "User created:\t${dummyUserName}",
                "Number of Mons:\t${dummyAmountOfMons}",
                "Number of shinies:\t${dummyAmountOfShinies}"
            )
        )
    }
}