package com.example.mymons.ui.catchMons

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mymons.models.Mon
import com.example.mymons.services.PokeApiService
import com.example.mymons.services.PokeApiServiceInterface
import kotlin.random.Random
import kotlinx.coroutines.launch

// Injecting the service is best practice. Here, we'll instantiate it for simplicity.
private val pokeApiService: PokeApiServiceInterface = PokeApiService()

@Composable
fun CatchPage() {
    // State to hold the result of the catch operation
    var caughtMon by remember { mutableStateOf<Mon?>(null) }
    // State to manage loading indicator
    var isLoading by remember { mutableStateOf(false) }
    // State to handle errors
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Coroutine scope for running suspending functions (like the API call)
    val coroutineScope = rememberCoroutineScope()

    // Function that handles the entire "Catch" logic
    val catchPokemon: () -> Unit = {
        // 1. Reset state
        caughtMon = null
        errorMessage = null
        isLoading = true

        coroutineScope.launch {
            try {
                // 2. Generate a random Pokémon ID
                val randomId = Random.nextInt(
                    PokeApiService.MIN_POKEMON_ID,
                    PokeApiService.MAX_POKEMON_ID + 1
                ) // +1 because nextInt is exclusive on the upper bound

                // 3. Call the API service
                val resultMon = pokeApiService.getMonById(randomId)

                // 4. Update the state with the result
                caughtMon = resultMon

            } catch (e: Exception) {
                // 5. Handle any network or API errors
                errorMessage = "Failed to catch a Pokémon! Error: ${e.message}"
                println(errorMessage)
            } finally {
                // 6. Stop loading regardless of success or failure
                isLoading = false
            }
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

        // --- Display Area ---
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
                    Text(errorMessage!!, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
                } else if (caughtMon != null) {
                    // Display the caught Pokémon details
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Use Coil for image loading from the URL
                        AsyncImage(
                            model = caughtMon!!.frontDefault,
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
                    Text("Click the button to try and catch a Pokémon!", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        // --- Catch Button ---
        Button(
            onClick = catchPokemon,
            enabled = !isLoading, // Disable button while loading
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