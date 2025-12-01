package com.example.mymons.models.auth

enum class Status {
    ERROR,
    OK
}

data class AuthResult(val user: User?, val status: Status) {
    fun isOk() = status == Status.OK
}
