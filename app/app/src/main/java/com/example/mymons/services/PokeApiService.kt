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
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Ignore fields we dont use
                prettyPrint = true
                isLenient = true
            })
        }
    }

    override suspend fun getMonById(id: Int): Mon {
        val url = "${POKEAPI_URL}pokemon/$id"

        try {
            val response: PokemonApiResponse = client.get(url).body()

            val isShiny = (0..100).random() < SHINY_ODDS
            return response.toMon(isShiny)
        } catch (e: Exception) {
            println("Error fetching Pokémon ID $id: ${e.message}")
            throw e
        }
    }

    companion object {
        const val POKEAPI_URL = "https://pokeapi.co/api/v2/"
        const val MAX_POKEMON_ID = 1025
        const val MIN_POKEMON_ID = 1
        const val SHINY_ODDS = 20
    }
}