package com.example.mymons.services

import com.example.mymons.firestoreModels.MonFS
import com.example.mymons.firestoreModels.toMonFS
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

    private fun getUserMonsCollection(uid: String) =
        db.collection(USER_COLLECTION_NAME)
            .document(uid)
            .collection(MON_COLLECTION_NAME)

    override suspend fun getMons(): List<Mon> {
        val uid = auth.currentUser?.uid ?: return emptyList()

        val userMonDoc = getUserMonsCollection(uid)
            .get()
            .await()

        val monsFS = userMonDoc.documents.mapNotNull { doc ->
            doc.toObject<MonFS>()
        }

        return monsFS.map {
            Mon(
                id = it.id.toString(),
                name = it.name,
                sprite = it.frontDefault,
                caughtDate = it.caughtDate.toDate(),
                catchLoc = it.catchLoc,
                isShiny = it.isShiny,
                hp = it.hp,
                attack = it.attack,
                defense = it.defense,
                specialAttack = it.specialAttack,
                specialDefend = it.specialDefend,
                speed = it.speed,
                type1 = it.type1,
                type2 = it.type2
            )
        }
    }

    suspend fun addMon(mon: Mon): Boolean {
        val uid = auth.currentUser?.uid ?: return false // Cannot save if not logged in

        // Convert the application Mon model to the Firestore MonFS model
        val monFS = mon.toMonFS()

        return try {
            // Use add() to automatically generate a document ID
            getUserMonsCollection(uid)
                .add(monFS)
                .await()
            true
        } catch (e: Exception) {
            println("Error saving Mon to Firestore: ${e.message}")
            false
        }
    }
}