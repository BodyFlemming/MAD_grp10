package com.example.mymons.ui.auth

import PrimaryButton
import SecondaryButton
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mymons.models.auth.Email
import com.example.mymons.models.auth.Password

@Composable
fun SignIn(
    signIn: (email: Email, password: Password) -> Unit,
    onNavigateToSingUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

        Row {
            Text("Email:")
            TextField(
                value = email,
                onValueChange = { email = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }

        Row {
            Text("Password:")
            TextField(
                value = password,
                onValueChange = { password = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        PrimaryButton(onClick = {
            if (!Email.validate(email) || !Password.validate(password)) {
                Log.v("SIMPLIFIED", "Invalid format: $email or $password")
            } else {
                signIn(Email(email), Password(password))
            }
        }) {
            Text("Sign In")
        }

        SecondaryButton(onClick = {
            onNavigateToSingUp()
        }) {
            Text("Sign Up")
        }
    }
}