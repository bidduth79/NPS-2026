package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.ui.components.ActionButtons
import com.example.ui.components.CalculationDetailsView
import com.example.ui.components.ResultDashboard
import com.example.ui.components.SalaryGrowthChart
import com.example.utils.CalculationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ResultSection(
    result: CalculationResult,
    scrollState: ScrollState,
    scope: CoroutineScope
) {
    var dashboardY by remember { mutableFloatStateOf(0f) }

    AnimatedContent(
        targetState = result,
        transitionSpec = {
            fadeIn(animationSpec = tween(400)) + slideInVertically(
                animationSpec = tween(400),
                initialOffsetY = { fullHeight -> fullHeight / 8 }
            ) togetherWith fadeOut(animationSpec = tween(200)) + slideOutVertically(
                animationSpec = tween(200),
                targetOffsetY = { fullHeight -> -fullHeight / 8 }
            )
        },
        label = "result_animation"
    ) { targetResult ->
        var detailsExpanded by remember { mutableStateOf(false) }
        var activeStage by remember { mutableIntStateOf(0) }
        
        val density = LocalDensity.current
        LaunchedEffect(detailsExpanded) {
            if (detailsExpanded) {
                scope.launch {
                    scrollState.animateScrollTo(dashboardY.roundToInt())
                }
            }
        }
        
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AnimatedContent(
                targetState = detailsExpanded,
                transitionSpec = {
                    fadeIn(animationSpec = tween(400, easing = FastOutSlowInEasing)) + 
                    scaleIn(initialScale = 0.95f, animationSpec = tween(400, easing = FastOutSlowInEasing)) togetherWith
                    fadeOut(animationSpec = tween(400, easing = FastOutSlowInEasing)) + 
                    scaleOut(targetScale = 0.95f, animationSpec = tween(400, easing = FastOutSlowInEasing))
                },
                label = "dashboard_layout_transition"
            ) { isExpanded ->
                if (isExpanded) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates -> 
                                val topOffset = with(density) { 32.dp.toPx() }
                                val newY = coordinates.positionInRoot().y + scrollState.value - topOffset
                                if (kotlin.math.abs(dashboardY - newY) > 1f) {
                                    dashboardY = newY
                                }
                            },
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            ResultDashboard(
                                result = targetResult, 
                                scrollOffset = scrollState.value,
                                activeStage = activeStage,
                                isCompact = true,
                                onClick = { activeStage = (activeStage + 1) % 4 }
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            SalaryGrowthChart(
                                result = targetResult, 
                                scrollOffset = scrollState.value,
                                isCompact = true
                            )
                        }
                    }
                } else {
                    Box(modifier = Modifier.onGloballyPositioned { coordinates -> 
                        val topOffset = with(density) { 32.dp.toPx() }
                        val newY = coordinates.positionInRoot().y + scrollState.value - topOffset
                        if (kotlin.math.abs(dashboardY - newY) > 1f) {
                            dashboardY = newY
                        }
                    }) {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            ResultDashboard(
                                result = targetResult, 
                                scrollOffset = scrollState.value,
                                activeStage = activeStage,
                                isCompact = false,
                                onClick = { activeStage = (activeStage + 1) % 4 }
                            )
                            
                            SalaryGrowthChart(
                                result = targetResult, 
                                scrollOffset = scrollState.value,
                                isCompact = false
                            )
                        }
                    }
                }
            }
            
            CalculationDetailsView(
                result = targetResult, 
                isExpanded = detailsExpanded,
                onToggle = { detailsExpanded = !detailsExpanded },
                onActiveStageChanged = { activeStage = it },
                onGraphClicked = { /* Handled internally by SalaryGrowthChart now */ }
            )
            ActionButtons(result = targetResult)
        }
    }
}
