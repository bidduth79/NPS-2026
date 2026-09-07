package com.example.ui.components.chart

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.LightRed

import com.example.ui.theme.ChartPurple

import com.example.ui.theme.ChartOrange

import com.example.ui.theme.ChartGreen

import com.example.ui.theme.TextGray

import com.example.ui.theme.DarkBorder

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utils.CalculationResult

@Composable
fun ChartFlipContent(
    displayIndex: Int,
    isCompact: Boolean,
    bars: List<ChartBarData>,
    selectedIndex: Int,
    maxSalary: Float,
    frontAnimationProgress: Float,
    result: CalculationResult,
    calcData: Map<String, Long>
) {
    when (displayIndex) {
        0 -> {
            FrontBarChart(isCompact, bars, selectedIndex, maxSalary, frontAnimationProgress)
        }
        1 -> {
            val animationProgress = remember { Animatable(0f) }
            LaunchedEffect(displayIndex) {
                if (displayIndex == 1) {
                    animationProgress.snapTo(0f)
                    animationProgress.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(1200, easing = FastOutSlowInEasing)
                    )
                }
            }
            BackLineChart(isCompact, result, animationProgress.value)
        }
        2 -> {
            // Salary Breakdown
            ChartInfoScreen("Salary Breakdown (Monthly)", isCompact) {
                InfoRow("Basic Salary", calcData["basic"] ?: 0L, ChartPurple, isCompact)
                InfoRow(stringResource(id = R.string.profile_house_rent), calcData["houseRent"] ?: 0L, ChartGreen, isCompact)
                InfoRow(stringResource(id = R.string.salary_medical), calcData["medical"] ?: 0L, Color.White, isCompact)
                InfoRow(stringResource(id = R.string.salary_education_allowance), calcData["education"] ?: 0L, Color.White, isCompact)
                if ((calcData["others"] ?: 0L) > 0) {
                    InfoRow(stringResource(id = R.string.profile_other_allowances), calcData["others"] ?: 0L, Color.White, isCompact)
                }
            }
        }
        3 -> {
            // Increment Details
            ChartInfoScreen("Increment Details", isCompact) {
                InfoRow("Previous Basic", result.currentBasic, TextGray, isCompact)
                InfoRow("Total Increment", result.details.totalIncrease, ChartGreen, isCompact)
                InfoRow("New Basic", result.convertedBasic, ChartPurple, isCompact, isBold = true)
                
                Spacer(modifier = Modifier.height(16.dp))
                Text("Annual Increment Rate: ~${result.details.annualIncrement}%", color = ChartOrange, fontSize = if (isCompact) 10.sp else 12.sp)
            }
        }
        4 -> {
            // Gross vs Net Pay
            ChartInfoScreen("Gross vs Net Pay", isCompact) {
                InfoRow("Total Gross Salary", calcData["totalGross"] ?: 0L, ChartGreen, isCompact, isBold = true)
                HorizontalDivider(color = DarkBorder, modifier = Modifier.padding(vertical = 8.dp))
                InfoRow("GPF Deduction", calcData["gpf"] ?: 0L, LightRed, isCompact)
                HorizontalDivider(color = DarkBorder, modifier = Modifier.padding(vertical = 8.dp))
                InfoRow("Net Payable Salary", calcData["net"] ?: 0L, ChartOrange, isCompact, isBold = true)
            }
        }
        5 -> {
            // Bonus & Allowances
            val festivalBonus = (calcData["basic"] ?: 0L)
            val boishakhiAllowance = ((calcData["basic"] ?: 0L) * 0.20).toLong()
            ChartInfoScreen("Bonus & Festivities", isCompact) {
                InfoRow("Festival Bonus (x2)", festivalBonus * 2, ChartGreen, isCompact)
                InfoRow("Boishakhi Allowance", boishakhiAllowance, ChartOrange, isCompact)
                HorizontalDivider(color = DarkBorder, modifier = Modifier.padding(vertical = 8.dp))
                InfoRow("Total Yearly Bonus", festivalBonus * 2 + boishakhiAllowance, Color.White, isCompact, isBold = true)
            }
        }
        6 -> {
            // Yearly GPF
            ChartInfoScreen("GPF Contribution", isCompact) {
                InfoRow("Monthly Deduction", calcData["gpf"] ?: 0L, LightRed, isCompact)
                InfoRow("Months", 12L, Color.White, isCompact, prefix = "x")
                HorizontalDivider(color = DarkBorder, modifier = Modifier.padding(vertical = 8.dp))
                InfoRow("Total Yearly GPF", (calcData["gpf"] ?: 0L) * 12, ChartGreen, isCompact, isBold = true)
                Spacer(modifier = Modifier.height(16.dp))
                Text("This is your forced savings for future.", color = TextGray, fontSize = if (isCompact) 10.sp else 12.sp)
            }
        }
    }
}
