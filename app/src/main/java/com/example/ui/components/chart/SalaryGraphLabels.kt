package com.example.ui.components.chart

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.ChartPurple

import com.example.ui.theme.ChartOrange

import com.example.ui.theme.ChartGreen

import com.example.ui.theme.ChartRed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utils.CalculationResult
import com.example.utils.NumberFormatter

@Composable
fun SalaryGraphLabels(result: CalculationResult) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val labels = listOf(stringResource(id = R.string.chart_old_basic), stringResource(id = R.string.chart_stage_1), stringResource(id = R.string.chart_stage_2), stringResource(id = R.string.chart_final))
        val values = listOf(result.currentBasic, result.stage1, result.stage2, result.convertedBasic)
        val colors = listOf(ChartRed, ChartGreen, ChartOrange, ChartPurple)
        
        for (i in 0..3) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Box(modifier = Modifier.size(8.dp).background(colors[i], CircleShape))
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = labels[i],
                    color = Color.Gray,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = NumberFormatter.format(values[i]),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
