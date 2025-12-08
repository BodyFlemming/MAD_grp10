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
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val type1: String,
    val type2: String?
)

@Serializable
data class PokemonApiResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val stats: List<Stats>,
    val types: List<TypeEntry>
)

@Serializable
data class Sprites(
    @SerialName("front_default")
    val frontDefault: String?,
    @SerialName("front_shiny")
    val frontShiny: String?
)

@Serializable
data class Stats(
    @SerialName("base_stat")
    val baseStat: Int,
)

@Serializable
data class TypeEntry(
    @SerialName("slot")
    val slot: Int,
    @SerialName("type")
    val type: TypeInfo
)

@Serializable
data class TypeInfo(
    @SerialName("name")
    val name: String
)


fun PokemonApiResponse.toMon(isShiny: Boolean): Mon {
    fun getTypeName(index: Int): String? {
        return this.types.getOrNull(index)?.type?.name?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
    }

    return Mon(
        id = this.id.toString(),
        name = this.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
        sprite = (if (isShiny) {
            this.sprites.frontShiny
        } else {
            this.sprites.frontDefault
        }) ?: "https://placehold.co/150x150/EEEEEE/333333?text=?",
        caughtDate = Date(),
        catchLoc = GeoPoint(56.15674, 10.21076),
        isShiny = isShiny,
        hp = this.stats[0].baseStat,
        attack = this.stats[1].baseStat,
        defense = this.stats[2].baseStat,
        specialAttack = this.stats[3].baseStat,
        specialDefense = this.stats[4].baseStat,
        speed = this.stats[5].baseStat,
        type1 = getTypeName(0) ?: "Normal",
        type2 = getTypeName(1)
    )
}