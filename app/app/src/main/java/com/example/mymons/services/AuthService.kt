package com.example.mymons.services

import com.example.mymons.models.auth.AuthResult
import com.example.mymons.models.auth.Email
import com.example.mymons.models.auth.Password
import com.example.mymons.models.auth.Status
import com.example.mymons.models.auth.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

interface AuthServiceInterface {
    suspend fun signup(email: Email, password: Password): AuthResult
    suspend fun signIn(email: Email, password: Password): AuthResult
    fun signOut()
}

class AuthService : AuthServiceInterface {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun signup(email: Email, password: Password): AuthResult {
        return try {
            val result = auth.createUserWithEmailAndPassword(email.value, password.value)
                .await() // Suspends until Firebase finishes
                .user
                ?: return AuthResult(null, Status.ERROR)

            val emailObj = result.email?.let { Email(it) }
                ?: return AuthResult(null, Status.ERROR)

            val user = User(result.uid, emailObj)

            AuthResult(user, Status.OK)
        } catch (e: Exception) {
            AuthResult(null, Status.ERROR)
        }
    }

    override suspend fun signIn(email: Email, password: Password): AuthResult {
        return try {
            val result = auth.signInWithEmailAndPassword(email.value, password.value)
                .await()
                .user
                ?: return AuthResult(null, Status.ERROR)

            // Converts the Firebase email string back into your Domain Email object
            val emailDomain = result.email?.let { Email(it) }
                ?: return AuthResult(null, Status.ERROR)

            val user = User(result.uid, emailDomain)

            AuthResult(user, Status.OK)
        } catch (e: Exception) {
            AuthResult(null, Status.ERROR)
        }
    }

    override fun signOut() {
        auth.signOut()
    }

}