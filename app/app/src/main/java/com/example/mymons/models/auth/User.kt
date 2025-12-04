package com.example.mymons.models.auth

import java.util.Date

data class User(
    val id: String,
    val email: Email,
    val creationDay: Date,
    val monCount: Int,
    val monShinyCount: Int
) {}