package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PixelCharacter(
    characterId: String,
    modifier: Modifier = Modifier
) {
    val character = getCharacterInfo(characterId)

    Box(
        modifier = modifier
            .size(64.dp)
            .background(character.bgColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = character.emoji,
            fontSize = 32.sp
        )
    }
}

private fun getCharacterInfo(characterId: String): CharacterInfo {
    return when (characterId) {
        "taffi" -> CharacterInfo("🧚", Color(0xFFFFE4FF))
        "doro" -> CharacterInfo("🐱", Color(0xFFFFE4E4))
        "gugaga" -> CharacterInfo("🦉", Color(0xFFE4E4FF))
        "pikachu" -> CharacterInfo("⚡", Color(0xFFFFFFE4))
        "momoyo" -> CharacterInfo("🌸", Color(0xFFFFE4E4))
        else -> CharacterInfo("👤", Color(0xFFE4E4E4))
    }
}

data class CharacterInfo(
    val emoji: String,
    val bgColor: Color
)