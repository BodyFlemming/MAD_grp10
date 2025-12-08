package com.example.mymons.services

import com.example.mymons.data.dto.UserFS
import com.example.mymons.models.auth.Email
import com.example.mymons.models.auth.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

interface UserServiceInterface {
    suspend fun getUser(): User?
}

class UserService : UserServiceInterface {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    companion object {
        const val USER_COLLECTION_NAME = "users"
    }

    override suspend fun getUser(): User? {
        val uid = auth.currentUser?.uid ?: return null

        val snapshot = db.collection(USER_COLLECTION_NAME)
            .document(uid)
            .get()
            .await()

        val userFS = snapshot.toObject<UserFS>() ?: return null

        return User(
            id = userFS.id ?: uid,
            email = Email(userFS.email),
            creationDay = userFS.creationDate.toDate(),
            pokemonAvatar = userFS.pokemonAvatar,
            name = userFS.name
        )
    }
}