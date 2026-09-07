package com.example.ui.components.chat

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.ChipBg

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AiChatQuickChips(onSuggestionClick: (String) -> Unit) {
    val suggestions = listOf(stringResource(id = R.string.chat_chip_pay_scale), stringResource(id = R.string.profile_tiffin_allowance), stringResource(id = R.string.chat_chip_pension), stringResource(id = R.string.chat_chip_festival))
    LazyRow(
        modifier = Modifier.padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(suggestions) { suggestion ->
            SuggestionChip(
                onClick = { onSuggestionClick(suggestion) },
                label = { Text(suggestion, color = Color.White) },
                colors = SuggestionChipDefaults.suggestionChipColors(containerColor = ChipBg),
                border = null
            )
        }
    }
}
