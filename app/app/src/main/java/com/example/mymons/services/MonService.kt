package com.example.mymons.services

import com.example.mymons.firestoreModels.MonFS
import com.example.mymons.models.Mon
import com.google.firebase.firestore.FirebaseFirestore
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
        val mons = db.collection(MON_COLLECTION_NAME).get().await()
        val monsFS = mons.documents.mapNotNull { documentSnapshot ->
            documentSnapshot.toObject<MonFS>()
        }
        return monsFS.map {
            val idAsInt = it.id?.toIntOrNull()
                ?: throw IllegalStateException("Mon ID is null or not a valid integer: ${it.id}")

            Mon(
                idAsInt,
                name = it.name,
                frontDefault = it.frontDefault,
                caughtDate = it.caughtDate.toDate()
            )
        }
    }
}