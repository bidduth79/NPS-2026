package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.data.UserProfile
import com.example.ui.components.profile.ProfileDialogBackground
import com.example.ui.components.profile.ProfileDialogHeader
import com.example.ui.components.profile.ProfileSaveButton
import com.example.ui.components.profile.ProfileBaseScaleSection
import com.example.ui.components.profile.ProfileHouseAllowanceSection
import com.example.ui.components.profile.ProfileOtherAllowancesSection
import com.example.ui.theme.DarkBackground

@Composable
fun UserProfileDialogContent(
    profile: UserProfile,
    onSave: (UserProfile) -> Unit,
    onDismiss: () -> Unit
) {
    var tempProfile by remember(profile) { mutableStateOf(profile) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        val scrollState = rememberScrollState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
        ) {
            // Parallax Background Effect
            ProfileDialogBackground(scrollState)

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Fixed Header
                ProfileDialogHeader(onDismiss = onDismiss)

                // Scrollable content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(scrollState)
                        .padding(bottom = 120.dp)
                ) {
                    ProfileBaseScaleSection(
                        profile = tempProfile,
                        onProfileChange = { tempProfile = it }
                    )
                    
                    ProfileHouseAllowanceSection(
                        profile = tempProfile,
                        onProfileChange = { tempProfile = it }
                    )
                    
                    ProfileOtherAllowancesSection(
                        profile = tempProfile,
                        onProfileChange = { tempProfile = it }
                    )
                }
            }

            // Save button pinned at bottom
            ProfileSaveButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = { onSave(tempProfile) }
            )
        }
    }
}
