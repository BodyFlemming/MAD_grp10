package com.example.mymons.services

interface PokeApiServiceInterface {
    suspend fun getMonSprite(name: String)
}

class PokeApiService : PokeApiServiceInterface {
    override suspend fun getMonSprite(name: String) {
        TODO("Not yet implemented")
    }

    companion object {
        const val POKEAPI_URL = "https://pokeapi.co/api/v2/"
    }


}