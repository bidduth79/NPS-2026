package com.example.ui.components.profile

import androidx.compose.ui.res.stringResource
import com.example.R
import com.example.ui.theme.DarkBorder
import com.example.data.UserProfile
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val BorderColor
    @androidx.compose.runtime.Composable get() = DarkBorder.copy(alpha = 0.5f)

@Composable
fun LocationSection(
    profile: UserProfile,
    onProfileChange: (UserProfile) -> Unit,
    locationType: String,
    onLocationChange: (String) -> Unit,
    hasFrontierAllowance: Boolean,
    onFrontierAllowanceChange: (Boolean) -> Unit,
    hillAllowancePercent: String,
    onHillAllowanceChange: (String) -> Unit,
    joiningDate: String,
    onJoiningDateChange: (String) -> Unit
) {
    SectionCard(
        title = stringResource(id = R.string.profile_location_regional),
        icon = Icons.Rounded.LocationOn,
        badge = stringResource(id = R.string.profile_house_rent)
    ) {
        Column {
            ToggleRow(
                title = "Marital Status",
                subtitle = if (profile.maritalStatus == "Married") "Married (Variable House Rent)" else "Unmarried (30% House Rent)",
                isChecked = profile.maritalStatus == "Married",
                onCheckedChange = { isMarried -> 
                    onProfileChange(profile.copy(maritalStatus = if (isMarried) "Married" else "Unmarried"))
                }
            )

            if (profile.maritalStatus == "Married") {
                Spacer(modifier = Modifier.height(16.dp))
                ToggleRow(
                    title = "Duty Type",
                    subtitle = if (!profile.isLineMan && profile.isFamilyMan) "Family Man (Conditional)" else "Line Man (50% House Rent)",
                    isChecked = !profile.isLineMan && profile.isFamilyMan,
                    onCheckedChange = { isFamilyMan ->
                        onProfileChange(profile.copy(isLineMan = !isFamilyMan, isFamilyMan = isFamilyMan))
                    }
                )

                if (profile.isFamilyMan) {
                    Spacer(modifier = Modifier.height(16.dp))
                    ToggleRow(
                        title = "Living Status",
                        subtitle = if (!profile.isInLiving) "Out-Living (Location Based)" else "In-Living (50% House Rent)",
                        isChecked = !profile.isInLiving,
                        onCheckedChange = { isOutLiving ->
                            onProfileChange(profile.copy(isInLiving = !isOutLiving))
                        }
                    )

                    if (!profile.isInLiving) {
                        Spacer(modifier = Modifier.height(16.dp))
                        LocationPostingArea(
                            profile = profile,
                            onProfileChange = onProfileChange
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = BorderColor, thickness = 1.dp)
            Spacer(modifier = Modifier.height(14.dp))
            
            RegionalAllowances(
                profile = profile,
                onProfileChange = onProfileChange
            )
        }
    }
}
