package com.example.mymons.ui.catchMons

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mymons.models.Mon
import com.example.mymons.services.LocationService
import com.example.mymons.services.MonService
import com.example.mymons.services.PokeApiService
import com.example.mymons.services.PokeApiServiceInterface
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.launch
import kotlin.random.Random

// Injecting the service is best practice. Here, we'll instantiate it for simplicity.
private val pokeApiService: PokeApiServiceInterface = PokeApiService()
private val monService: MonService = MonService()

@Composable
fun CatchPage() {
    // State to hold the result of the catch operation
    var caughtMon by remember { mutableStateOf<Mon?>(null) }
    // State to manage loading indicator
    var isLoading by remember { mutableStateOf(false) }
    // State to handle errors
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val locationService = remember { LocationService(context) }

    val catchPokemon: (GeoPoint) -> Unit = { location ->
        caughtMon = null
        errorMessage = null
        isLoading = true

        coroutineScope.launch {
            try {
                val randomId = Random.nextInt(
                    PokeApiService.MIN_POKEMON_ID,
                    PokeApiService.MAX_POKEMON_ID + 1
                )

                val apiMon = pokeApiService.getMonById(randomId)

                val finalMon = apiMon.copy(catchLoc = location)

                val isSuccess = monService.addMon(finalMon)
                if (!isSuccess) {
                    throw Exception("Failed to save the caught Pokémon to the database.")
                }

                caughtMon = finalMon

            } catch (e: Exception) {
                errorMessage = "Failed to catch a Pokémon! Error: ${e.message}"
                println(errorMessage)
            } finally {
                isLoading = false
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (granted) {
            coroutineScope.launch {
                val loc = locationService.getCurrentLocation()
                if (loc != null) catchPokemon(loc)
                else errorMessage = "Could not fetch GPS signal"
            }
        } else {
            errorMessage = "Permission needed to catch Pokemon!"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Ready to Catch?",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else if (errorMessage != null) {
                    Text(
                        errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                } else if (caughtMon != null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = caughtMon!!.sprite,
                            contentDescription = "${caughtMon!!.name} sprite",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(150.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "#${caughtMon!!.id} ${caughtMon!!.name}",
                            fontSize = 24.sp,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Caught: ${caughtMon!!.caughtDate}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                } else {
                    Text(
                        "Click the button to try and catch a Pokémon!",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Button(
            onClick = {
                isLoading = true
                if (locationService.hasPermission()) {
                    coroutineScope.launch {
                        val loc = locationService.getCurrentLocation()
                        if (loc != null) catchPokemon(loc)
                    }
                } else {
                    permissionLauncher.launch(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                    )
                }
            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = if (isLoading) "Searching..." else "CATCH!",
                fontSize = 20.sp
            )
        }
    }
}