package com.example.ui.components.profile

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.DarkCardInner

import com.example.ui.theme.DarkCard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.Info
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
private val TextSecondary
    @androidx.compose.runtime.Composable get() = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant

@Composable
fun BaseScaleSection(
    allowanceBaseScale: String,
    onScaleChange: (String) -> Unit
) {
    SectionCard(
        title = stringResource(id = R.string.profile_base_scale),
        icon = Icons.Rounded.AccountBalance,
        badge = "Base"
    ) {
        Text(
            text = "Select applicable pay scale",
            color = TextSecondary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ScaleSelectionCard(
                title = "2015 Scale",
                subtitle = "Previous rules & rates",
                isSelected = allowanceBaseScale == "2015",
                onClick = { onScaleChange("2015") },
                modifier = Modifier.weight(1f)
            )
            ScaleSelectionCard(
                title = "2026 Scale",
                subtitle = "Proposed new rates",
                isSelected = allowanceBaseScale == "2026",
                onClick = { onScaleChange("2026") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Explanatory note matching theme
        Surface(
            color = if (allowanceBaseScale == "2026") Color(0xFF132238) else InnerCardBg,
            shape = RoundedCornerShape(12.dp),
            border = if (allowanceBaseScale == "2026") BorderStroke(1.dp, Color(0xFF2563EB).copy(alpha = 0.5f)) else null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = null,
                    tint = if (allowanceBaseScale == "2026") Color(0xFF60A5FA) else TextSecondary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = if (allowanceBaseScale == "2026") {
                        "2026 Scale: Medical allowance 3,000 ৳, Education 1,000 ৳, Tiffin 600 ৳ and House Rent computed on new basic pay."
                    } else {
                        "2015 Scale: Allowances calculated using standard 2015 government pay scale rules and previous rates."
                    },
                    color = if (allowanceBaseScale == "2026") Color(0xFFBFDBFE) else TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }
        }
    }
}
