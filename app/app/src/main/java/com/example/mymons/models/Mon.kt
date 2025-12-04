package com.example.mymons.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

import com.google.firebase.firestore.GeoPoint

data class Mon(
    val id: Int,
    val name: String,
    val frontDefault: String,
    val caughtDate: java.util.Date,
    val catchLoc: GeoPoint,
)

@Serializable
data class PokemonApiResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites
)

@Serializable
data class Sprites(
    @SerialName("front_default")
    val frontDefault: String
)

fun PokemonApiResponse.toMon(): Mon {
    return Mon(
        id = this.id,
        name = this.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }, // Capitalize name
        frontDefault = this.sprites.frontDefault ?: "https://placehold.co/150x150/EEEEEE/333333?text=?", // Provide fallback URL
        caughtDate = Date(),
        catchLoc = GeoPoint(0.0, 0.0) // placeholder
    )
}