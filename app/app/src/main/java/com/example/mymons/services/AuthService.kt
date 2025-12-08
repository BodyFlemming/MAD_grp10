package com.example.mymons.services

import android.util.Log
import com.example.mymons.data.dto.UserFS
import com.example.mymons.models.auth.AuthResult
import com.example.mymons.models.auth.Email
import com.example.mymons.models.auth.Password
import com.example.mymons.models.auth.Status
import com.example.mymons.models.auth.User
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import java.util.Date

interface AuthServiceInterface {
    suspend fun signup(name: String, email: Email, password: Password, avatar: String): AuthResult
    suspend fun signIn(email: Email, password: Password): AuthResult
    fun signOut()
}

class AuthService : AuthServiceInterface {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun signup(name: String, email: Email, password: Password, avatar: String): AuthResult {
        return try {
            val result = auth.createUserWithEmailAndPassword(email.value, password.value)
                .await()
                .user
                ?: return AuthResult(null, Status.ERROR)

            val newUserFS = UserFS(
                id = result.uid,
                email = email.value,
                name = name,
                pokemonAvatar = avatar,
                creationDate = Timestamp.now(),
            )

            db.collection("users")
                .document(result.uid)
                .set(newUserFS)
                .await()

            val emailObj = result.email?.let { Email(it) }
                ?: return AuthResult(null, Status.ERROR)

            val user = User(
                id = result.uid,
                email = emailObj,
                creationDay = Date(),
                pokemonAvatar = avatar,
                name = name
            )

            AuthResult(user, Status.OK)
        } catch (e: Exception) {
            Log.e("SignUp", "Error: ${e.message}")
            AuthResult(null, Status.ERROR)
        }
    }

    override suspend fun signIn(email: Email, password: Password): AuthResult {
        return try {
            val result = auth.signInWithEmailAndPassword(email.value, password.value)
                .await()
                .user
                ?: return AuthResult(null, Status.ERROR)

            val snapshot = db.collection("users")
                .document(result.uid)
                .get()
                .await()

            val userFS = snapshot.toObject<UserFS>()
                ?: return AuthResult(null, Status.ERROR)

            val emailDomain = result.email?.let { Email(it) }
                ?: return AuthResult(null, Status.ERROR)

            val user = User(
                id = result.uid,
                email = emailDomain,
                creationDay = userFS.creationDate.toDate(),
                pokemonAvatar = userFS.pokemonAvatar,
                name = userFS.name
            )

            AuthResult(user, Status.OK)
        } catch (e: Exception) {
            AuthResult(null, Status.ERROR)
        }
    }

    override fun signOut() {
        auth.signOut()
    }

}