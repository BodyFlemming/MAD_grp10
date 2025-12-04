package com.example.mymons.services

import com.example.mymons.firestoreModels.MonFS
import com.example.mymons.models.Mon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

interface MonServiceInterface {
    suspend fun getMons(): List<Mon>
}

class MonService : MonServiceInterface {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    companion object {
        const val MON_COLLECTION_NAME = "mons"
        const val USER_COLLECTION_NAME = "users"
    }

    override suspend fun getMons(): List<Mon> {
        val uid = auth.currentUser?.uid ?: return emptyList()

        val userMonDoc = db.collection(USER_COLLECTION_NAME)
            .document(uid)
            .collection(MON_COLLECTION_NAME)
            .get()
            .await()

        val monsFS = userMonDoc.documents.mapNotNull { doc ->
            doc.toObject<MonFS>()
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