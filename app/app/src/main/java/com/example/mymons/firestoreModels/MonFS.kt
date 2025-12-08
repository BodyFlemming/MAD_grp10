package com.example.mymons.firestoreModels

import com.example.mymons.models.Mon
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint

data class MonFS(
    @DocumentId var id: String? = null,
    val name: String = "",
    val frontDefault: String = "",
    val caughtDate: Timestamp = Timestamp.now(),
    val catchLoc: GeoPoint = GeoPoint(0.0, 0.0),
    val isShiny: Boolean = false,
    val hp: Int = 0,
    val attack: Int = 0,
    val defense: Int = 0,
    val specialAttack: Int = 0,
    val specialDefend: Int = 0,
    val speed: Int = 0,
    val type1: String = "Normal",
    val type2: String? = null
)

fun Mon.toMonFS(): MonFS {
    return MonFS(
        // Set id to null so Firestore generates a new document ID on add()
        id = null,
        name = this.name,
        frontDefault = this.sprite,
        // Convert application Date to Firestore Timestamp
        caughtDate = Timestamp(this.caughtDate),
        catchLoc = this.catchLoc,
        isShiny = this.isShiny,
        hp = this.hp,
        attack = this.attack,
        defense = this.defense,
        specialAttack = this.specialAttack,
        specialDefend = this.specialDefend,
        speed = this.speed,
        type1 = this.type1,
        type2 = this.type2
    )
}