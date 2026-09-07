package com.example.ui.components.profile

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.AccentPurpleLight

import com.example.ui.theme.AccentPurple

import com.example.ui.theme.DarkBorder

import com.example.ui.theme.DarkCardInner

import com.example.ui.theme.DarkCard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val InnerCardBg
    @androidx.compose.runtime.Composable get() = DarkCardInner
private val IconBg = AccentPurple.copy(alpha = 0.2f)
private val IconTint = AccentPurpleLight
private val TextSecondary
    @androidx.compose.runtime.Composable get() = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
private val TextPrimary
    @androidx.compose.runtime.Composable get() = androidx.compose.material3.MaterialTheme.colorScheme.onSurface

@Composable
fun ChildrenSection(
    allowanceBaseScale: String,
    numberOfChildren: Int,
    onNumberOfChildrenChange: (Int) -> Unit,
    numberOfDisabledChildren: String,
    onDisabledChildrenChange: (String) -> Unit
) {
    SectionCard(
        title = "Children",
        icon = Icons.Rounded.School,
        badge = "Education"
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Eligible for Education",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = if (allowanceBaseScale == "2026") "1,000 ৳/child (Max 2)" else "500 ৳/child (Max 2)",
                    color = IconTint,
                    fontSize = 11.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val childOptions = listOf(
                    0 to "0",
                    1 to "1",
                    2 to "2 or more"
                )
                childOptions.forEach { (count, label) ->
                    val isSelected = (count == 0 && numberOfChildren == 0) ||
                            (count == 1 && numberOfChildren == 1) ||
                            (count == 2 && numberOfChildren >= 2)
                    Surface(
                        onClick = { onNumberOfChildrenChange(count) },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) IconBg else InnerCardBg,
                        border = if (isSelected) BorderStroke(1.dp, IconTint) else null,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                color = if (isSelected) Color.White else TextSecondary,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider(color = DarkBorder.copy(alpha = 0.5f), thickness = 1.dp)
        Spacer(modifier = Modifier.height(14.dp))

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.profile_disabled_children),
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "3,000 ৳ per child",
                    color = Color(0xFF34D399),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            ModernCompactField(
                value = numberOfDisabledChildren,
                onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) onDisabledChildrenChange(it) },
                suffix = "Children",
                placeholder = "0"
            )
        }
    }
}
