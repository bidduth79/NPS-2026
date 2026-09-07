package com.example.data

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UserProfile(
    val numberOfChildren: Int = 0,
    val hasTiffinAllowance: Boolean = true,
    val hasWashingAllowance: Boolean = true,
    val locationType: String = "Other Locations", // Dhaka, City, Other
    val hasFrontierAllowance: Boolean = false,
    val joiningDate: String = "",
    val hillAllowancePercent: Int = 0, // Deprecated, kept for backward compatibility if needed, or we can just ignore it
    val hasHillAllowance: Boolean = false,
    val hillAllowanceAreaType: String = "Sadar", // "Sadar" or "Other"
    val numberOfDisabledChildren: Int = 0,
    val mobileBillAmount: Long = 0L,
    val tradeAllowanceAmount: Long = 0L,
    val gpfDeduction: Long = 0L,
    val selectedGradeIndex: Int = -1,
    val selectedStepIndex: Int = -1,
    val selectedStageName: String = "AUTO",
    val allowanceBaseScale: String = "2015",
    
    // New fields added
    val selectedScale: String = "2026", 
    val maritalStatus: String = "Unmarried",
    val isLineMan: Boolean = false,
    val isFamilyMan: Boolean = true,
    val isInLiving: Boolean = false,
    val hasFestivalAllowance: Boolean = true,
    val hasBaishakhiAllowance: Boolean = true,
    val recreationAllowanceAmount: Long = 0L,
    val awardAllowanceAmount: Long = 0L,
    val appLanguage: String = "en"
)

class UserProfileManager private constructor(context: Context) {
    private val prefs = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    private val _profile = MutableStateFlow(loadProfile())
    val profile: StateFlow<UserProfile> = _profile.asStateFlow()

    private fun loadProfile(): UserProfile {
        return UserProfile(
            numberOfChildren = prefs.getInt("children", 0),
            hasTiffinAllowance = prefs.getBoolean("tiffin_allowance", true),
            hasWashingAllowance = prefs.getBoolean("washing_allowance", true),
            locationType = prefs.getString("location", "Other Locations") ?: "Other Locations",
            hasFrontierAllowance = prefs.getBoolean("has_frontier_allowance", false),
            joiningDate = prefs.getString("joining_date", "") ?: "",
            hillAllowancePercent = prefs.getInt("hill_allowance_percent", 0),
            hasHillAllowance = prefs.getBoolean("has_hill_allowance", false),
            hillAllowanceAreaType = prefs.getString("hill_allowance_area_type", "Sadar") ?: "Sadar",
            numberOfDisabledChildren = prefs.getInt("num_disabled_children", 0),
            mobileBillAmount = prefs.getLong("mobile_bill_amount", 0L),
            tradeAllowanceAmount = prefs.getLong("trade_allowance_amount", 0L),
            gpfDeduction = prefs.getLong("gpf_deduction", 0L),
            selectedGradeIndex = prefs.getInt("selected_grade_index", -1),
            selectedStepIndex = prefs.getInt("selected_step_index", -1),
            selectedStageName = prefs.getString("selected_stage_name", "AUTO") ?: "AUTO",
            allowanceBaseScale = prefs.getString("allowance_base_scale", "2015") ?: "2015",
            
            selectedScale = prefs.getString("selected_scale", "2026") ?: "2026",
            maritalStatus = prefs.getString("marital_status", "Unmarried") ?: "Unmarried",
            isLineMan = prefs.getBoolean("is_line_man", false),
            isFamilyMan = prefs.getBoolean("is_family_man", true),
            isInLiving = prefs.getBoolean("is_in_living", false),
            hasFestivalAllowance = prefs.getBoolean("has_festival_allowance", true),
            hasBaishakhiAllowance = prefs.getBoolean("has_baishakhi_allowance", true),
            recreationAllowanceAmount = prefs.getLong("recreation_allowance_amount", 0L),
            awardAllowanceAmount = prefs.getLong("award_allowance_amount", 0L),
            appLanguage = prefs.getString("app_language", "en") ?: "en"
        )
    }

    fun saveProfile(newProfile: UserProfile) {
        prefs.edit {
            putInt("children", newProfile.numberOfChildren)
            putBoolean("tiffin_allowance", newProfile.hasTiffinAllowance)
            putBoolean("washing_allowance", newProfile.hasWashingAllowance)
            putString("location", newProfile.locationType)
            putBoolean("has_frontier_allowance", newProfile.hasFrontierAllowance)
            putString("joining_date", newProfile.joiningDate)
            putInt("hill_allowance_percent", newProfile.hillAllowancePercent)
            putBoolean("has_hill_allowance", newProfile.hasHillAllowance)
            putString("hill_allowance_area_type", newProfile.hillAllowanceAreaType)
            putInt("num_disabled_children", newProfile.numberOfDisabledChildren)
            putLong("mobile_bill_amount", newProfile.mobileBillAmount)
            putLong("trade_allowance_amount", newProfile.tradeAllowanceAmount)
            putLong("gpf_deduction", newProfile.gpfDeduction)
            putInt("selected_grade_index", newProfile.selectedGradeIndex)
            putInt("selected_step_index", newProfile.selectedStepIndex)
            putString("selected_stage_name", newProfile.selectedStageName)
            putString("allowance_base_scale", newProfile.allowanceBaseScale)
            
            putString("selected_scale", newProfile.selectedScale)
            putString("marital_status", newProfile.maritalStatus)
            putBoolean("is_line_man", newProfile.isLineMan)
            putBoolean("is_family_man", newProfile.isFamilyMan)
            putBoolean("is_in_living", newProfile.isInLiving)
            putBoolean("has_festival_allowance", newProfile.hasFestivalAllowance)
            putBoolean("has_baishakhi_allowance", newProfile.hasBaishakhiAllowance)
            putLong("recreation_allowance_amount", newProfile.recreationAllowanceAmount)
            putLong("award_allowance_amount", newProfile.awardAllowanceAmount)
            putString("app_language", newProfile.appLanguage)
        }
        _profile.value = newProfile
    }
    companion object {
        @Volatile
        private var INSTANCE: UserProfileManager? = null

        fun getInstance(context: Context): UserProfileManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserProfileManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
