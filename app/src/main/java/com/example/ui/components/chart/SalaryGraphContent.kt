package com.example.ui.components.chart

import androidx.compose.runtime.Composable
import com.example.utils.CalculationResult

@Composable
fun SalaryGraphContent(result: CalculationResult) {
    SalaryGraphCanvas(result = result)
    SalaryGraphLabels(result = result)
}
