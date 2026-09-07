package com.example.ui.components.profile

import com.example.ui.theme.AccentPurpleLight

import com.example.ui.theme.AccentPurple

import com.example.ui.theme.DarkCardInner

import com.example.ui.theme.DarkCard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val InnerCardBg
    @androidx.compose.runtime.Composable get() = DarkCardInner
private val IconTint = AccentPurpleLight
private val TextPrimary
    @androidx.compose.runtime.Composable get() = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
private val TextSecondary
    @androidx.compose.runtime.Composable get() = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant

@Composable
fun SwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                color = TextSecondary,
                fontSize = 12.sp
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = AccentPurple,
                uncheckedThumbColor = TextSecondary,
                uncheckedTrackColor = Color.White.copy(alpha = 0.1f),
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}

@Composable
fun ModernCompactField(
    value: String,
    onValueChange: (String) -> Unit,
    suffix: String,
    placeholder: String = ""
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            if (placeholder.isNotEmpty()) {
                Text(placeholder, color = TextSecondary, fontSize = 14.sp)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        trailingIcon = {
            Text(
                text = suffix,
                color = TextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 12.dp)
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedBorderColor = IconTint,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = InnerCardBg,
            unfocusedContainerColor = InnerCardBg
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

fun String.toEnglishDigits(): String {
    return this.map { char ->
        when (char) {
            '০' -> '0'
            '১' -> '1'
            '২' -> '2'
            '৩' -> '3'
            '৪' -> '4'
            '৫' -> '5'
            '৬' -> '6'
            '৭' -> '7'
            '৮' -> '8'
            '৯' -> '9'
            else -> char
        }
    }.joinToString("")
}
