package com.example.ui.components

import com.example.ui.theme.ErrorRed

import com.example.ui.theme.AccentPurpleLight

import com.example.ui.theme.AccentPurple

import com.example.ui.theme.DarkBorder

import com.example.ui.theme.DarkCardAlternative

import com.example.ui.theme.DarkCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BluePrimary
import com.example.utils.NumberFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    stepNumber: String,
    items: List<String>,
    selectedIndex: Int,
    placeholder: String = "",
    onItemSelected: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(start = 4.dp)
        ) {
            val fullText = "$stepNumber. $label"
            Text(
                text = buildAnnotatedString {
                    val startIndex = fullText.indexOf("(")
                    if (startIndex != -1) {
                        append(fullText.substring(0, startIndex))
                        withStyle(style = SpanStyle(color = ErrorRed)) { // Red color for parenthesis part
                            append(fullText.substring(startIndex))
                        }
                    } else {
                        append(fullText)
                    }
                },
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = AccentPurpleLight // Light purple accent
            )
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(AccentPurple.copy(alpha = 0.5f), RoundedCornerShape(50))
            )
        }
        
        var expanded by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }
        
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                color = DarkCard,
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (selectedIndex >= 0 && selectedIndex < items.size) items[selectedIndex] else placeholder,
                        color = if (selectedIndex >= 0) androidx.compose.material3.MaterialTheme.colorScheme.onSurface else androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            }
            
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(DarkCardAlternative)
            ) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { 
                            Text(
                                text = item,
                                color = if (index == selectedIndex) AccentPurpleLight else androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                            ) 
                        },
                        onClick = {
                            onItemSelected(index)
                            expanded = false
                        },
                        modifier = Modifier.background(
                            if (index == selectedIndex) DarkBorder.copy(alpha = 0.5f)
                            else Color.Transparent
                        )
                    )
                }
            }
        }
    }
}
