package com.example.mymons.services

import com.example.mymons.models.Mon
import com.example.mymons.models.PokemonApiResponse
import com.example.mymons.models.toMon
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Get mon by ID
interface PokeApiServiceInterface {
    suspend fun getMonById(id: Int): Mon
}

class PokeApiService : PokeApiServiceInterface {
    // Ktor HTTP Client setup
    private val client = HttpClient {
        // Configure JSON serialization using kotlinx.serialization
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Ignore fields we dont use
                prettyPrint = true
                isLenient = true
            })
        }
    }

    override suspend fun getMonById(id: Int): Mon {
        // Construct the full URL for the API call
        val url = "${POKEAPI_URL}pokemon/$id"
        println("Fetching Pokémon from: $url") // Helpful for debugging

        try {
            val response: PokemonApiResponse = client.get(url).body()

            // Map the API response to the data class
            return response.toMon()
        } catch (e: Exception) {
            println("Error fetching Pokémon ID $id: ${e.message}")
            throw e
        }
    }

    companion object {
        const val POKEAPI_URL = "https://pokeapi.co/api/v2/"
        const val MAX_POKEMON_ID = 1025 // Pokemon count in api, max value
        const val MIN_POKEMON_ID = 1
    }
}