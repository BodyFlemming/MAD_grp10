package com.example.mymons.services

import com.example.mymons.firestoreModels.HorseFS
import com.example.mymons.models.Horse
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await


interface HorseServiceInterface {
    suspend fun getHorses(): List<Horse>
}

class HorseService : HorseServiceInterface {

    // Singleton
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    companion object {
        const val HORSES_COLLECTION_NAME = "Horses"
    }

    override suspend fun getHorses(): List<Horse> {
        val horses = db.collection(HORSES_COLLECTION_NAME).get().await()
        val horsesFS = horses.documents.mapNotNull { documentSnapshot ->
            documentSnapshot.toObject<HorseFS>()
        }
        return horsesFS.map { Horse(it.id ?: throw IllegalStateException("Id invalid"), it.name) }
    }

}