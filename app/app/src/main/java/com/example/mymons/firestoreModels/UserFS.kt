package com.example.mymons.data.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class UserFS(
    @DocumentId var id: String? = null,
    val email: String = "",
    val userName: String = "",
    val creationDate: Timestamp = Timestamp.now(),
    val monCount: Int = 0,
    val monShinyCount: Int = 0
)