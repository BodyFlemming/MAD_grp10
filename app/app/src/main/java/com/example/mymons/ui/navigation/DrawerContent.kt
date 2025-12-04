package com.example.mymons.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    isLoggedIn: Boolean
) {
    ModalDrawerSheet {
        Text("My Mons App", modifier = Modifier.padding(16.dp))
        HorizontalDivider()

        if (isLoggedIn) {
            NavigationDrawerItem(
                label = { Text("My mons") },
                selected = false,
                onClick = {
                    navController.navigate("mons")
                    scope.launch { drawerState.close() }
                }
            )

            NavigationDrawerItem(
                label = { Text("Catch Mon") },
                selected = false,
                onClick = {
                    navController.navigate("catchMons")
                    scope.launch { drawerState.close() }
                }
            )

            NavigationDrawerItem(
                label = { Text("User") },
                selected = false,
                onClick = {
                    navController.navigate("users")
                    scope.launch { drawerState.close() }
                }
            )

        } else {
            NavigationDrawerItem(
                label = { Text("Sign In") },
                selected = false,
                onClick = {
                    navController.navigate("signIn")
                    scope.launch { drawerState.close() }
                }
            )

            NavigationDrawerItem(
                label = { Text("Sign Up") },
                selected = false,
                onClick = {
                    navController.navigate("signUp")
                    scope.launch { drawerState.close() }
                }
            )
        }
    }
}