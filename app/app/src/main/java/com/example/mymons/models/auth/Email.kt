package com.example.mymons.models.auth

import android.util.Patterns

data class Email(val value: String) {
    init {
        if (!validate(value)) {
            throw IllegalArgumentException("ILLEGAL_MAIL")
        }
    }

    companion object {
//        Regex method, from slides
//        private val emailRegex = Regex(
//            """[a-zA-Z0-9\+\.\_\%\-\+]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\-]{0,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{0,25})+"""
//        )

        fun validate(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}