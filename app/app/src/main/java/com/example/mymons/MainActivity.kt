package com.example.mymons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymons.models.Horse
import com.example.mymons.models.Mon
import com.example.mymons.services.AuthService
import com.example.mymons.services.HorseService
import com.example.mymons.services.MonService
import com.example.mymons.ui.auth.SignIn
import com.example.mymons.ui.auth.SignUp
import com.example.mymons.ui.horse.Horses
import com.example.mymons.ui.mons.Mons
import com.example.mymons.ui.navigation.DrawerContent
import com.example.mymons.ui.navigation.TopBar
import com.example.mymons.ui.theme.MyMonsTheme
import kotlinx.coroutines.launch

// TODO Rename everything Horse to Pokemon
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMonsTheme {
                // Remembers the current page
                val navController = rememberNavController()

                // Remembers if drawer is closed or open
                val drawerState = rememberDrawerState(DrawerValue.Closed)

                // Used to run the open/close animation of the drawer
                val scope = rememberCoroutineScope()

                // Authentication setup
                val isLoggedIn = remember { mutableStateOf(true) }
                val authService: AuthService = remember { AuthService() }
                LaunchedEffect(key1 = isLoggedIn.value) {
                    if (!isLoggedIn.value) {
                        navController.navigate(route = "signIn")
                    } else {
                        navController.navigate(route = "mons")
                    }
                }

                ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
                    DrawerContent(navController, scope, drawerState)
                }) {
                    Scaffold(topBar = {
                        TopBar(onMenuClicked = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        })
                    }) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "signIn",
                            Modifier
                                .padding(innerPadding)
                                .padding(horizontal = 16.dp)
                        ) {
                            composable(route = "signUp") {
                                SignUp { email, password ->
                                    scope.launch {
                                        val res = authService.signup(email, password)
                                        isLoggedIn.value = res.isOk()
                                    }
                                }
                            }

                            composable(route = "signIn") {
                                SignIn { email, password ->
                                    scope.launch {
                                        val res = authService.signIn(email, password)
                                        isLoggedIn.value = res.isOk()
                                    }
                                }
                            }

                            composable(route = "horses") {
                                if (isLoggedIn.value) {
                                    val horseService: HorseService = remember { HorseService() }
                                    val horses = remember { mutableStateOf(listOf<Horse>()) }

                                    LaunchedEffect(Unit) {
                                        horses.value = horseService.getHorses()
                                    }

                                    Horses(horses.value)
                                }
                            }

                            composable(route = "mons") {
                                if (isLoggedIn.value) {
                                    val monService: MonService = remember { MonService() }
                                    val mons = remember { mutableStateOf(listOf<Mon>()) }

                                    LaunchedEffect(Unit) {
                                        mons.value = monService.getMons()
                                    }

                                    Mons(mons.value)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}