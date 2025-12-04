package com.example.mymons.data.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class UserFS(
    @DocumentId var id: String? = null,
    val email: String = "",
    val creationDate: Timestamp = Timestamp.now(),
)