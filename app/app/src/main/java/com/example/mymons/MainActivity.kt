package com.example.mymons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import com.example.mymons.services.HorseService
import com.example.mymons.ui.navigation.DrawerContent
import com.example.mymons.ui.navigation.TopBar
import com.example.mymons.ui.theme.MyMonsTheme
import kotlinx.coroutines.launch

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
                            startDestination = "firstItem",
                            Modifier
                                .padding(innerPadding)
                                .padding(horizontal = 16.dp)
                        ) {
                            composable("firstItem") {
                                FirstItem {
                                    navController.navigate("secondItem")
                                }
                            }
                            composable("secondItem") {
                                SecondItem {
                                    navController.navigate("thirdItem")
                                }
                            }
                            composable("thirdItem") {
                                ThirdItem {
                                    navController.navigate(("firstItem"))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FirstItem(navigate: () -> Unit) {
    val state = remember { mutableStateOf(listOf<Horse>()) }
    LaunchedEffect(Unit) {
        state.value = HorseService().getHorses()
    }

    Column {
        Text(state.value.firstOrNull()?.id ?: "Loading...")
        Text(state.value.firstOrNull()?.name ?: "Loading...")
        Button(onClick = navigate) {
            Text("Navigate to Second Item")
        }
    }
}

@Composable
fun SecondItem(navigate: () -> Unit) {
    Column {
        Text("Second Item")
        Button(onClick = navigate) {
            Text("Navigate to Third Item")
        }
    }
}

@Composable
fun ThirdItem(navigate: () -> Unit) {
    BackHandler {
        navigate()
    }

    Column {
        Text("Third Item")
        Text("End of road")
    }
}