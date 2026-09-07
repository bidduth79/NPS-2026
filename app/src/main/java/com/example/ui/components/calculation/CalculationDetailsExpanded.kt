package com.example.ui.components.calculation

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.PurpleAccent

import com.example.ui.theme.ChartPurple

import com.example.ui.theme.ChartOrange

import com.example.ui.theme.ChartGreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utils.CalculationResult
import kotlinx.coroutines.launch

@Composable
fun CalculationDetailsExpanded(
    result: CalculationResult,
    isExpanded: Boolean,
    onActiveStageChanged: (Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(pagerState.currentPage) {
        onActiveStageChanged(pagerState.currentPage + 1)
    }
    
    LaunchedEffect(isExpanded) {
        if (!isExpanded) {
            onActiveStageChanged(0)
        } else {
            onActiveStageChanged(pagerState.currentPage + 1)
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            contentColor = Color.White,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = PurpleAccent
                )
            }
        ) {
            val tabs = listOf(stringResource(id = R.string.chart_stage_1), stringResource(id = R.string.chart_stage_2), stringResource(id = R.string.chart_final))
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { 
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                ) {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }
        
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            when (page) {
                0 -> StageCard(
                    result = result,
                    stageNumber = 1,
                    date = "01-07-2026",
                    headerColor = Color(0xFF0F9D58),
                    highlightAmountColor = ChartGreen
                )
                1 -> StageCard(
                    result = result,
                    stageNumber = 2,
                    date = "01-01-2027",
                    headerColor = Color(0xFFE67C00),
                    highlightAmountColor = ChartOrange
                )
                2 -> Stage3Card(
                    result = result,
                    headerColor = PurpleAccent,
                    highlightAmountColor = ChartPurple
                )
            }
        }
    }
}
