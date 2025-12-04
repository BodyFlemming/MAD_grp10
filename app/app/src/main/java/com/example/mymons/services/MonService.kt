package com.example.mymons.services

import com.example.mymons.firestoreModels.MonFS
import com.example.mymons.models.Mon
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

interface MonServiceInterface {
    suspend fun getMons(): List<Mon>
}

class MonService : MonServiceInterface {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    companion object {
        const val MON_COLLECTION_NAME = "mons"
    }

    override suspend fun getMons(): List<Mon> {
        // return dummy mon list
        return listOf(
            Mon(
                id = "1",
                name = "Pikachu",
                frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
                caughtDate = java.util.Date(),
                catchLoc = GeoPoint(56.15674, 10.21076)
            ),
            Mon(
                id = "2",
                name = "Charizard",
                frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
                caughtDate = java.util.Date(),
                catchLoc = GeoPoint(56.15674, 10.21076)
            ),
            Mon(
                id = "3",
                name = "Bulbasaur",
                frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                caughtDate = java.util.Date(),
                catchLoc = GeoPoint(56.15674, 10.21076)
            )
        )
//        val mons = db.collection(MON_COLLECTION_NAME).get().await()
//        val monsFS = mons.documents.mapNotNull { documentSnapshot ->
//            documentSnapshot.toObject<MonFS>()
//        }
//        return monsFS.map {
//            Mon(
//                it.id ?: throw IllegalStateException("ID IS NULL"),
//                name = it.name,
//                frontDefault = it.frontDefault,
//                caughtDate = it.caughtDate.toDate(),
//                // create a dummy catchLoc
//                catchLoc = GeoPoint(56.15674, 10.21076)
//            )
//        }
    }
}