package com.example.mymons.ui.userDashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymons.data.dto.UserFS
import com.example.mymons.services.AuthService
import com.example.mymons.ui.global.BulletList
import com.example.mymons.ui.global.ImageCard
import com.example.mymons.ui.theme.White
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun UserDashboardPage(user: UserFS, onSignOut: () -> Unit) {
    val dummyAmountOfMons = 22
    val dummyAmountOfShinies = 2

    val authService: AuthService = remember { AuthService() }
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    Column(
        verticalArrangement = Arrangement.spacedBy(64.dp)
    ) {
        ImageCard(user.pokemonAvatar) {
            Text(user.name, fontSize = 30.sp, color = White)
        }

        BulletList(
            listOf(
                "Email:\t${user.email}",
                "Number of Mons:\t${dummyAmountOfMons}",
                "Number of shinies:\t${dummyAmountOfShinies}"
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