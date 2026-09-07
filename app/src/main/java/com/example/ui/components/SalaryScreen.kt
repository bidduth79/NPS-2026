package com.example.ui.components

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.DarkCard

import com.example.ui.theme.PureDark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PayScale2015
import com.example.data.PayScale2026
import com.example.data.UserProfileManager

import com.example.utils.SalaryCalculator
import kotlin.math.roundToLong

enum class ImplementationStage(val title: String) {
    AUTO("Automatic (By Date)"),
    STAGE_1("Stage 1 (50%) - July 2026"),
    STAGE_2("Stage 2 (75%) - January 2027"),
    FINAL("Final (100%) - July 2027")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalaryScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val userProfileManager = remember { UserProfileManager.getInstance(context) }
    val profile by userProfileManager.profile.collectAsState()
    val selectedGradeIndex = profile.selectedGradeIndex
    val selectedStepIndex = profile.selectedStepIndex
    val selectedStage = try { ImplementationStage.valueOf(profile.selectedStageName) } catch (e: Exception) { ImplementationStage.AUTO }
    
    val calendar = java.util.Calendar.getInstance()
    val year = calendar.get(java.util.Calendar.YEAR)
    val month = calendar.get(java.util.Calendar.MONTH) // 0-based, 6 is July

    val activeStage = if (selectedStage == ImplementationStage.AUTO) {
        when {
            year < 2026 -> ImplementationStage.STAGE_1
            year == 2026 && month < 6 -> ImplementationStage.STAGE_1 // Before July 2026
            year == 2026 && month >= 6 -> ImplementationStage.STAGE_1 // July 2026 - Dec 2026
            year == 2027 && month < 6 -> ImplementationStage.STAGE_2 // Jan 2027 - June 2027
            else -> ImplementationStage.FINAL // July 2027 onwards
        }
    } else {
        selectedStage
    }
    
    val isScale2026 = profile.selectedScale == "2026"

    val gradeList = PayScale2015.steps.keys.sorted().toList()
    val gradeOptions = gradeList.map { "Grade $it" }
    
    val selectedGrade = if (selectedGradeIndex in gradeList.indices) gradeList[selectedGradeIndex] else null
    val stepOptions = selectedGrade?.let { grade ->
        if (isScale2026) {
            PayScale2026.steps[grade]?.mapIndexed { index, amount -> 
                "Step ${index + 1} - ৳$amount"
            }
        } else {
            PayScale2015.steps[grade]?.mapIndexed { index, amount -> 
                 val calcResult = SalaryCalculator.calculate(grade, index)
                val amount2026 = calcResult?.convertedBasic ?: 0
                "Step ${index + 1} - (2015) ৳$amount ➔ (2026) ৳$amount2026"
            }
        }
    } ?: listOf("Please select a grade first")

    val finalBasicAmount = if (selectedGrade != null) {
        if (isScale2026) {
            val stepsList = PayScale2026.steps[selectedGrade]
            if (stepsList != null && selectedStepIndex in stepsList.indices) {
                stepsList[selectedStepIndex].toLong()
            } else null
        } else {
            val stepsList = PayScale2015.steps[selectedGrade]
            if (stepsList != null && selectedStepIndex in stepsList.indices) {
                val calcResult = SalaryCalculator.calculate(selectedGrade, selectedStepIndex)
                if (calcResult != null) {
                    when (activeStage) {
                        ImplementationStage.STAGE_1 -> calcResult.stage1
                        ImplementationStage.STAGE_2 -> calcResult.stage2
                        ImplementationStage.FINAL -> calcResult.stage3
                        else -> calcResult.stage3
                    }
                } else null
            } else null
        }
    } else null

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PureDark)
            .verticalScroll(scrollState)
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 100.dp)
    ) {
        Text(
            text = stringResource(id = R.string.salary_breakdown),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkCard),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Grade Dropdown
                DropdownSelector(
                    label = "Grade",
                    stepNumber = "1",
                    items = gradeOptions,
                    selectedIndex = selectedGradeIndex,
                    placeholder = stringResource(id = R.string.select_grade),
                    onItemSelected = { index ->
                        userProfileManager.saveProfile(profile.copy(selectedGradeIndex = index, selectedStepIndex = -1))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Step Dropdown
                DropdownSelector(
                    label = "Step",
                    stepNumber = "2",
                    items = stepOptions,
                    selectedIndex = selectedStepIndex,
                    placeholder = stringResource(id = R.string.select_step_basic),
                    onItemSelected = { index ->
                        if (selectedGrade != null) {
                            userProfileManager.saveProfile(profile.copy(selectedStepIndex = index))
                        }
                    }
                )
                
                if (!isScale2026) {
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Stage Dropdown
                    DropdownSelector(
                        label = "Implementation Stage",
                        stepNumber = "3",
                        items = ImplementationStage.values().map { it.title },
                        selectedIndex = ImplementationStage.values().indexOf(selectedStage),
                        onItemSelected = { index ->
                            userProfileManager.saveProfile(profile.copy(selectedStageName = ImplementationStage.values()[index].name))
                        }
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
// Result Display
        if (selectedGrade != null && finalBasicAmount != null) {
            val basic = finalBasicAmount
            
            val locDhaka = stringResource(id = R.string.loc_dhaka)
            val locOtherCity = stringResource(id = R.string.loc_other_city)
            val allowances = com.example.ui.components.salary.calculateDetailedAllowances(basic, selectedGrade, profile, selectedStepIndex, locDhaka, locOtherCity)
            
            com.example.ui.components.salary.DetailedCalculationCard(basic, profile, allowances)
            
            com.example.ui.components.salary.FutureIncrementForecast(
                grade = selectedGrade,
                currentStepIndex = selectedStepIndex,
                isScale2026 = isScale2026
            )
        }
    }
}

