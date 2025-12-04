package com.example.mymons.ui.auth

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mymons.models.auth.Email
import com.example.mymons.models.auth.Password
import coil.compose.AsyncImage

@Composable
fun SignUp(signUp: (name: String, email: Email, password: Password, avatar: String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    var selectedPokemon by remember {
        mutableStateOf(
            mapOf(
                "Bulbasaur" to "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                "Charmander" to "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
                "Squirtle" to "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png"
            )
        )
    }
    var selectedPokemonName by remember { mutableStateOf("Bulbasaur") }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

        Row {
            Text("Name:")
            TextField(
                value = name,
                onValueChange = { name = it }
            )
        }
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

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Column {
                AsyncImage(
                    modifier = Modifier.size(80.dp),
                    model = selectedPokemon["Bulbasaur"],
                    contentDescription = "Bulbasaur sprite"
                )
                RadioButton(
                    selected = selectedPokemonName == "Bulbasaur",
                    onClick = { selectedPokemonName = "Bulbasaur" }
                )
                Text("Bulbasaur")
            }
            Column {
                AsyncImage(
                    modifier = Modifier.size(80.dp),
                    model = selectedPokemon["Charmander"],
                    contentDescription = "Charmander sprite"
                )
                RadioButton(
                    selected = selectedPokemonName == "Charmander",
                    onClick = { selectedPokemonName = "Charmander" }
                )
                Text("Charmander")
            }
            Column {
                AsyncImage(
                    modifier = Modifier.size(80.dp),
                    model = selectedPokemon["Squirtle"],
                    contentDescription = "Squirtle sprite"
                )
                RadioButton(
                    selected = selectedPokemonName == "Squirtle",
                    onClick = { selectedPokemonName = "Squirtle" }
                )
                Text("Squirtle")
            }
        }



        Button(onClick = {
            if (!Email.validate(email) || !Password.validate(password)) {
                Log.v("SIMPLIFIED", "Error in $email or $password")
            } else {
                // Create the objects and call the callback
                signUp(
                    name,
                    Email(email),
                    Password(password),
                    selectedPokemon[selectedPokemonName]!!
                )
            }
        }) {
            Text("Sign Up")
        }
    }
}