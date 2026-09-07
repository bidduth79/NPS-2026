package com.example.ui.components.profile

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.DarkBorder

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val TextPrimary
    @androidx.compose.runtime.Composable get() = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
private val BorderColor
    @androidx.compose.runtime.Composable get() = DarkBorder.copy(alpha = 0.5f)

@Composable
fun OtherAllowancesSection(
    allowanceBaseScale: String,
    hasTiffinAllowance: Boolean,
    onTiffinAllowanceChange: (Boolean) -> Unit,
    hasWashingAllowance: Boolean,
    onWashingAllowanceChange: (Boolean) -> Unit,
    mobileBillAmount: String,
    onMobileBillAmountChange: (String) -> Unit,
    tradeAllowanceAmount: String,
    onTradeAllowanceAmountChange: (String) -> Unit
) {
    SectionCard(
        title = stringResource(id = R.string.profile_other_allowances),
        icon = Icons.Rounded.Work,
        badge = "Tiffin & Bills"
    ) {
        SwitchRow(
            title = stringResource(id = R.string.profile_tiffin_allowance),
            subtitle = if (allowanceBaseScale == "2026") "600 ৳/month (2026 Scale)" else "200 ৳/month (2015 Scale)",
            checked = hasTiffinAllowance,
            onCheckedChange = onTiffinAllowanceChange
        )

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(color = BorderColor, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))

        SwitchRow(
            title = "Washing / Barber Allowance",
            subtitle = "300 ৳/month",
            checked = hasWashingAllowance,
            onCheckedChange = onWashingAllowanceChange
        )

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(color = BorderColor, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Mobile Bill",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                ModernCompactField(
                    value = mobileBillAmount,
                    onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) onMobileBillAmountChange(it) },
                    suffix = "৳",
                    placeholder = "0"
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Trade Allowance",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                ModernCompactField(
                    value = tradeAllowanceAmount,
                    onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) onTradeAllowanceAmountChange(it) },
                    suffix = "৳",
                    placeholder = "0"
                )
            }
        }
    }
}

@Composable
fun DeductionsSection(
    gpfDeduction: String,
    onGpfDeductionChange: (String) -> Unit
) {
    SectionCard(
        title = stringResource(id = R.string.profile_deductions),
        icon = Icons.Rounded.RemoveCircle,
        badge = "GPF"
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "GPF Deduction",
                color = TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(6.dp))
            ModernCompactField(
                value = gpfDeduction,
                onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) onGpfDeductionChange(it) },
                suffix = "৳",
                placeholder = "0"
            )
        }
    }
}
