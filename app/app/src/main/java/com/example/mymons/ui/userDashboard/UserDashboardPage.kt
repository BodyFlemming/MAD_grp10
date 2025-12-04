package com.example.mymons.ui.userDashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymons.models.auth.User
import com.example.mymons.services.AuthService
import com.example.mymons.services.UserService
import com.example.mymons.ui.global.BulletList
import com.example.mymons.ui.global.ImageCard
import com.example.mymons.ui.theme.White

@Composable
fun UserDashboardPage(onSignOut: () -> Unit) {
    val dummyAmountOfMons = 22
    val dummyAmountOfShinies = 2

    val authService: AuthService = remember { AuthService() }
    val userService: UserService = remember { UserService() }

    val user = remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) {
        user.value = userService.getUser()
    }

    user.value?.let { currentUser ->
        Column(
            verticalArrangement = Arrangement.spacedBy(64.dp)
        ) {
            ImageCard(currentUser.pokemonAvatar) {
                Text(currentUser.name, fontSize = 30.sp, color = White)
            }

            BulletList(
                listOf(
                    "Email:\t${currentUser.email.value}",
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
}