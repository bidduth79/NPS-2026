package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.components.navigation.FloatingAiButton
import com.example.ui.components.navigation.MainBottomBar
import com.example.ui.components.navigation.ExpandedMoreMenu

@Composable
fun CustomBottomNavigation(
    scrollState: ScrollState, 
    selectedItem: Int, 
    onItemSelected: (Int) -> Unit, 
    onAiButtonClicked: () -> Unit,
    onSalarySetClicked: () -> Unit = {},
    onApiKeyClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit = {},
    onAboutClicked: () -> Unit = {},
    onGpfClicked: () -> Unit = {},
    onPensionClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(true) }
    var previousScroll by remember { mutableIntStateOf(0) }
    var isMoreExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(scrollState.value) {
        val currentScroll = scrollState.value
        val delta = currentScroll - previousScroll
        if (currentScroll == 0) {
            isVisible = true
        } else if (delta > 15) {
            isVisible = false
            isMoreExpanded = false
        } else if (delta < -15) {
            isVisible = true
        }
        previousScroll = currentScroll
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it * 2 }),
        exit = slideOutVertically(targetOffsetY = { it * 2 }),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Expandable Secondary Bar
            ExpandedMoreMenu(
                isExpanded = isMoreExpanded,
                onPensionClicked = {
                    isMoreExpanded = false
                    onPensionClicked()
                },
                onSalarySetClicked = {
                    isMoreExpanded = false
                    onSalarySetClicked()
                },
                onApiKeyClicked = {
                    isMoreExpanded = false
                    onApiKeyClicked()
                },
                onSettingsClicked = {
                    isMoreExpanded = false
                    onSettingsClicked()
                },
                onAboutClicked = {
                    isMoreExpanded = false
                    onAboutClicked()
                },
                onGpfClicked = {
                    isMoreExpanded = false
                    onGpfClicked()
                }
            )

            // Main Bar and FAB Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(84.dp)
            ) {
                // Background bar (Flush with the bottom)
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    MainBottomBar(
                        selectedItem = selectedItem,
                        isMoreExpanded = isMoreExpanded,
                        onItemSelected = { 
                            onItemSelected(it)
                            isMoreExpanded = false 
                        },
                        onMoreClicked = { isMoreExpanded = !isMoreExpanded }
                    )
                }
                
                // Floating Center Button
                FloatingAiButton(
                    onClick = onAiButtonClicked,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}
