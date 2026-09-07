package com.example.ui.components


import androidx.compose.animation.core.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Rules
import com.example.utils.CalculationResult
import com.example.utils.NumberFormatter
import com.example.ui.components.calculation.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset

@Composable
fun CalculationDetailsView(
    result: CalculationResult,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onActiveStageChanged: (Int) -> Unit = {},
    onGraphClicked: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CalculationDetailsHeader(isExpanded = isExpanded, onToggle = onToggle)
        
        AnimatedVisibility(visible = isExpanded) {
            CalculationDetailsExpanded(
                result = result,
                isExpanded = isExpanded,
                onActiveStageChanged = onActiveStageChanged
            )
        }
    }
}
