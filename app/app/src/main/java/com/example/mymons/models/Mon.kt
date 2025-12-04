package com.example.mymons.models

import com.google.firebase.firestore.GeoPoint

data class Mon(
    val id: String,
    val name: String,
    val frontDefault: String,
    val caughtDate: java.util.Date,
    val catchLoc: GeoPoint,
) {

}
