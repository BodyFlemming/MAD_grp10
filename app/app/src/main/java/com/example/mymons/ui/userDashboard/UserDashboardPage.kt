package com.example.mymons.ui.userDashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymons.models.auth.User
import com.example.mymons.services.AuthService
import com.example.mymons.ui.global.BulletList
import com.example.mymons.ui.global.ImageCard
import com.example.mymons.ui.theme.White

@Composable
fun UserDashboardPage(user: User, onSignOut: () -> Unit) {
    val authService: AuthService = remember { AuthService() }

    Column(
        verticalArrangement = Arrangement.spacedBy(64.dp)
    ) {
        ImageCard("") {
            Text(user.userName, fontSize = 30.sp, color = White)
        }

        BulletList(
            listOf(
                "User created:\t${user.creationDay}",
                "Number of Mons:\t${user.monCount}",
                "Number of shinies:\t${user.monShinyCount}"
            )
        )

        Button(
            onClick = {
                authService.signOut()
                onSignOut()
            },
            enabled = true,
            content = { Text("Sign out") }
        )
    }
}