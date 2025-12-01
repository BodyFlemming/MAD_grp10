package com.example.mymons.models.auth

data class Password(val value: String) {
    init {
        if (!validate(value)) {
            throw IllegalArgumentException("ILLEGAL_PASSWORD")
        }
    }

    companion object {
        private val regexPattern =
            Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}$")

        fun validate(password: String): Boolean {
            return regexPattern.matches(password)
        }
    }
}