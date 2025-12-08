package com.example.mymons.models.auth

import android.util.Patterns

data class Email(val value: String) {
    init {
        if (!validate(value)) {
            throw IllegalArgumentException("ILLEGAL_MAIL")
        }
    }

    companion object {
        fun validate(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}