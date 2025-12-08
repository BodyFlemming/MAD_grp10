package com.example.mymons

import CatchLocationScreen
import MonDetailScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mymons.models.Mon
import com.example.mymons.services.AuthService
import com.example.mymons.services.MonService
import com.example.mymons.ui.auth.SignIn
import com.example.mymons.ui.auth.SignUp
import com.example.mymons.ui.catchMons.CatchPage
import com.example.mymons.ui.mons.MonsPage
import com.example.mymons.ui.navigation.DrawerContent
import com.example.mymons.ui.navigation.TopBar
import com.example.mymons.ui.theme.MyMonsTheme
import com.example.mymons.ui.userDashboard.UserDashboardPage
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMonsTheme {
                // Remembers the current page
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val currentPage = when (currentRoute) {
                    "mons" -> "My Mons"
                    "signIn" -> "Sign In"
                    "signUp" -> "Sign Up"
                    "monDetail" -> "Mon Detail"
                    "catchLocation" -> "Catch Location"
                    "users" -> "User Dashboard"
                    else -> "My Mons"
                }

                // Remembers if drawer is closed or open
                val drawerState = rememberDrawerState(DrawerValue.Closed)

                // Used to run the open/close animation of the drawer
                val scope = rememberCoroutineScope()
                var selectedMon by remember { mutableStateOf<Mon?>(null) }

                // Authentication setup
                val isLoggedIn = remember { mutableStateOf(false) }
                val authService: AuthService = remember { AuthService() }
                LaunchedEffect(key1 = isLoggedIn.value) {
                    if (!isLoggedIn.value) {
                        navController.navigate(route = "signIn")
                    } else {
                        navController.navigate(route = "mons")
                    }
                }

                ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
                    DrawerContent(navController, scope, drawerState, isLoggedIn.value)
                }) {
                    Scaffold(
                        containerColor = Color(0xFF202636),
                        topBar = {
                            TopBar(
                                title = currentPage,
                                onMenuClicked = {
                                    scope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                })
                        }) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "mons",
                            Modifier
                                .padding(innerPadding)
                                .padding(horizontal = 16.dp, vertical = 16.dp),
                        ) {
                            composable(route = "signUp") {
                                SignUp { name, email, password, avatar ->
                                    scope.launch {
                                        val res = authService.signup(name, email, password, avatar)
                                        isLoggedIn.value = res.isOk()
                                    }
                                }
                            }

                            composable(route = "signIn") {
                                SignIn(
                                    signIn = { email, password ->
                                        scope.launch {
                                            val res = authService.signIn(email, password)
                                            isLoggedIn.value = res.isOk()
                                        }
                                    },
                                    onNavigateToSingUp = {
                                        navController.navigate("signUp")
                                    })
                            }

                            composable(route = "mons") {
                                if (isLoggedIn.value) {
                                    val monService: MonService = remember { MonService() }
                                    val mons = remember { mutableStateOf(listOf<Mon>()) }

                                    LaunchedEffect(Unit) {
                                        mons.value = monService.getMons()
                                    }

                                    MonsPage(mons.value) { mon ->
                                        selectedMon = mon
                                        navController.navigate("monDetail")
                                    }
                                }
                            }

                            composable(route = "monDetail") {
                                selectedMon?.let { mon ->
                                    MonDetailScreen(
                                        mon = mon,
                                        onBackClick = { navController.popBackStack() },
                                        onCatchLocationClick = { navController.navigate("catchLocation") }
                                    )
                                }
                            }

                            composable(route = "catchLocation") {
                                selectedMon?.let { mon ->
                                    CatchLocationScreen(
                                        mon = mon,
                                        onBackClick = { navController.popBackStack() }
                                    )
                                }
                            }

                            composable(route = "catchMons") {
                                if (isLoggedIn.value) {
                                    CatchPage()
                                }
                            }

                            composable(route = "users") {
                                if (isLoggedIn.value) {
                                    UserDashboardPage(
                                        onSignOut = {
                                            isLoggedIn.value = false
                                            navController.navigate("signIn") {
                                                popUpTo("users") { inclusive = true }
                                            }
                                        })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}