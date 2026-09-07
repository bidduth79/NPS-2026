package com.example.ui.screens

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.DarkBorder

import com.example.ui.theme.DarkCard

import com.example.ui.theme.DarkBackground

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.data.PayScale2015
import com.example.ui.components.*
import com.example.utils.NumberFormatter
import com.example.utils.SalaryCalculator
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun HomeScreen() {
    val grades = (1..20).map { stringResource(id = R.string.grade_format, it) }
    var selectedGradeIndex by remember { mutableIntStateOf(15) } // Default Grade 16 is index 15
    var selectedStepIndex by remember { mutableIntStateOf(15) } // Default Step 16
    val grade = selectedGradeIndex + 1
    val stepsList = PayScale2015.steps[grade] ?: emptyList()
    
    val stepsDropdown = stepsList.mapIndexed { index, value -> 
        "Step ${index + 1} — ${NumberFormatter.format(value)}"
    }

    // Reset step index if grade changes and previous step index is out of bounds
    LaunchedEffect(grade) {
        if (selectedStepIndex >= stepsList.size) {
            selectedStepIndex = 0
        }
    }
    
    val result = SalaryCalculator.calculate(grade, selectedStepIndex)
    val scrollState = rememberScrollState()
    var currentScreen by remember { mutableIntStateOf(0) }
    var showAiSheet by remember { mutableStateOf(false) }
    var showApiKeyDialog by remember { mutableStateOf(false) }
    var showUserProfileDialog by remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DeveloperSidebar(
                onWalletClicked = { 
                    currentScreen = 5 
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = DarkBackground // Dark premium background
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (currentScreen == 0) {
                    ParallaxBackground(scrollOffset = scrollState.value)
                    
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(bottom = 100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AppHeader(
                            scrollOffset = scrollState.value,
                            onMenuClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        )
                    
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = (-32).dp)
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Input Card
                            Surface(
                                shape = RoundedCornerShape(32.dp),
                                color = DarkCard.copy(alpha = 0.8f), // Glassy dark card
                                shadowElevation = 0.dp,
                                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder.copy(alpha = 0.5f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    DropdownSelector(
                                        label = stringResource(id = R.string.select_grade),
                                        stepNumber = "1",
                                        items = grades,
                                        selectedIndex = selectedGradeIndex,
                                        onItemSelected = { selectedGradeIndex = it }
                                    )
                                    
                                    DropdownSelector(
                                        label = stringResource(id = R.string.select_step_basic),
                                        stepNumber = "2",
                                        items = stepsDropdown,
                                        selectedIndex = selectedStepIndex,
                                        onItemSelected = { selectedStepIndex = it }
                                    )
                                }
                            }
                            
                            // Dashboard
                            if (result != null) {
                                ResultSection(result, scrollState, scope)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        } // closes inner Column
                    } // closes scrolling Column
                } else if (currentScreen == 1) {
                    GazetteScreen()
                } else if (currentScreen == 2) {
                    SalaryScreen()
                } else if (currentScreen == 3) {
                    com.example.ui.components.gpf.GpfScreen(onBack = { currentScreen = 0 })
                } else if (currentScreen == 4) {
                    com.example.ui.components.pension.PensionScreen(onBack = { currentScreen = 0 })
                } else if (currentScreen == 5) {
                    WalletScreen()
                }
                
                // Custom Navigation Bar fixed at the bottom
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    CustomBottomNavigation(
                        scrollState = scrollState, 
                        selectedItem = currentScreen, 
                        onItemSelected = { currentScreen = it }, 
                        onAiButtonClicked = { showAiSheet = true },
                        onSalarySetClicked = { showUserProfileDialog = true },
                        onApiKeyClicked = { showApiKeyDialog = true },
                        onGpfClicked = { currentScreen = 3 },
                        onPensionClicked = { currentScreen = 4 }
                    )
                }
            } // closes Box
        } // closes Scaffold
        
        if (showAiSheet) {
            AiChatSheet(onDismiss = { showAiSheet = false })
        }
        
        if (showApiKeyDialog) {
            ApiKeyDialog(onDismiss = { showApiKeyDialog = false })
        }
        
        if (showUserProfileDialog) {
            UserProfileDialog(onDismiss = { showUserProfileDialog = false })
        }
    } // closes ModalNavigationDrawer
}
