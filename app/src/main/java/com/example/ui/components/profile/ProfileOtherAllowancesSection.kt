package com.example.ui.components.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.data.UserProfile

@Composable
fun ProfileOtherAllowancesSection(
    profile: UserProfile,
    onProfileChange: (UserProfile) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ProfileOtherAllowancesCard(profile, onProfileChange)
        ProfileDeductionsCard(profile, onProfileChange)
    }
}
