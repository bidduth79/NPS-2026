package com.example.ui.components.dashboard

import androidx.compose.runtime.Composable

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.ChartPurple

import com.example.ui.theme.ChartOrange

import com.example.ui.theme.ChartGreen

import com.example.ui.theme.ChartRed

import androidx.compose.ui.graphics.Color
import com.example.utils.CalculationResult

data class DashboardState(
    val mainTitle: String,
    val mainAmount: Long,
    val subTitle1: String,
    val subAmount1: Long,
    val subTitle2: String,
    val subAmount2: Long,
    val mainAmountColor: Color,
    val subAmount1Color: Color
)



@Composable
fun getDashboardState(displayedStage: Int, isCompact: Boolean, result: CalculationResult): DashboardState {
    val mainTitle = when (displayedStage) {
        1 -> if (isCompact) "Stage-1 (Jul '26)" else "Stage-1 (01 Jul 2026)"
        2 -> if (isCompact) "Stage-2 (Jan '27)" else "Stage-2 (01 Jan 2027)"
        3 -> if (isCompact) "Final (Jul '27)" else "Final Basic (01 Jul 2027)"
        else -> if (isCompact) "New (Jul '27)" else "New Basic (1 Jul 2027)"
    }
    
    val mainAmount = when (displayedStage) {
        1 -> result.stage1
        2 -> result.stage2
        3 -> result.convertedBasic
        else -> result.convertedBasic
    }
    
    val subTitle1 = when (displayedStage) {
        1 -> stringResource(id = R.string.chart_old_basic)
        2 -> stringResource(id = R.string.chart_old_basic)
        3 -> stringResource(id = R.string.chart_old_basic)
        else -> "Stage-1 (01 Jul 2026)"
    }
    
    val subAmount1 = when (displayedStage) {
        1 -> result.currentBasic
        2 -> result.currentBasic
        3 -> result.currentBasic
        else -> result.stage1
    }
    
    val subTitle2 = when (displayedStage) {
        1 -> "Increase (50%)"
        2 -> "Increase (75%)"
        3 -> "Increase (100%)"
        else -> "Stage-2 (01 Jan 2027)"
    }
    
    val subAmount2 = when (displayedStage) {
        1 -> result.stage1 - result.currentBasic
        2 -> result.stage2 - result.currentBasic
        3 -> result.convertedBasic - result.currentBasic
        else -> result.stage2
    }

    val mainAmountColor = when (displayedStage) {
        1 -> ChartGreen // Green
        2 -> ChartOrange // Amber/Orange
        3 -> ChartPurple // Purple
        else -> Color.White
    }

    val subAmount1Color = when (displayedStage) {
        1, 2, 3 -> ChartRed // Red for Old Basic
        else -> Color.White
    }

    return DashboardState(
        mainTitle, mainAmount, subTitle1, subAmount1, subTitle2, subAmount2, mainAmountColor, subAmount1Color
    )
}
