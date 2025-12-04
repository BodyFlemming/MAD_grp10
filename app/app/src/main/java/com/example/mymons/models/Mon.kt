package com.example.mymons.models

import com.google.firebase.firestore.GeoPoint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

data class Mon(
    val id: String,
    val name: String,
    val sprite: String,
    val caughtDate: Date,
    val catchLoc: GeoPoint,
    val isShiny: Boolean,
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
    val frontDefault: String?,
    @SerialName("front_shiny")
    val frontShiny: String?
)

fun PokemonApiResponse.toMon(isShiny: Boolean): Mon {
    return Mon(
        id = this.id.toString(),
        name = this.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
        sprite = (if (isShiny) {
            this.sprites.frontShiny
        } else {
            this.sprites.frontDefault
        }) ?: "https://placehold.co/150x150/EEEEEE/333333?text=?",
        caughtDate = java.util.Date(),
        catchLoc = GeoPoint(56.15674, 10.21076),
        isShiny = isShiny
    )
}