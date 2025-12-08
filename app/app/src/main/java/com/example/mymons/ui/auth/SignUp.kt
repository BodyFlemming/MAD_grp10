package com.example.mymons.ui.auth

import PrimaryButton
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymons.models.auth.Email
import com.example.mymons.models.auth.Password
import com.example.mymons.ui.components.LabelAndField
import com.example.mymons.ui.theme.White

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

        LabelAndField(
            label = "Name:",
            value = name,
            onValueChange = { name = it }
        )

        LabelAndField(
            label = "Email:",
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        LabelAndField(
            label = "Password:",
            value = password,
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            PokemonOption(
                "Bulbasaur",
                selectedPokemon["Bulbasaur"],
                selectedPokemonName == "Bulbasaur",
                { selectedPokemonName = "Bulbasaur" }
            )
            PokemonOption(
                "Charmander",
                selectedPokemon["Charmander"],
                selectedPokemonName == "Charmander",
                { selectedPokemonName = "Charmander" }
            )
            PokemonOption(
                "Squirtle",
                selectedPokemon["Squirtle"],
                selectedPokemonName == "Squirtle",
                { selectedPokemonName = "Squirtle" }
            )
        }



        PrimaryButton(
            onClick = {
                if (!Email.validate(email) || !Password.validate(password)) {
                    Log.v("SIMPLIFIED", "Error in $email or $password")
                } else {
                    signUp(
                        name,
                        Email(email),
                        Password(password),
                        selectedPokemon[selectedPokemonName]!!
                    )
                }
            },
        ) {
            Text("Sign Up")
        }
    }
}

@Composable
fun PokemonOption(
    name: String,
    imageUrl: String?,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val backgroundColor =
        if (isSelected) White else Color.Transparent
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onSelect() }
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(backgroundColor)
            .padding(8.dp)
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            modifier = Modifier.size(80.dp),
            model = imageUrl,
            contentDescription = "$name sprite"
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
        )

        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}