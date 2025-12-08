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
    val shiny: Boolean = false
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
        shiny = this.isShiny
    )
}