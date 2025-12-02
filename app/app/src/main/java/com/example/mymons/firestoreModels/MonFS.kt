package com.example.mymons.firestoreModels

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class MonFS(
    @DocumentId var id: String? = null,
    val name: String = "",
    val frontDefault: String = "",
    val caughtDate: Timestamp = Timestamp.now()
)
