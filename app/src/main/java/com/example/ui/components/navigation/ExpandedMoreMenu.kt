package com.example.ui.components.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.Elderly
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.VpnKey
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.SecondaryBarBg

@Composable
fun ExpandedMoreMenu(
    isExpanded: Boolean,
    onPensionClicked: () -> Unit = {},
    onGpfClicked: () -> Unit = {},
    onSalarySetClicked: () -> Unit = {},
    onApiKeyClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit,
    onAboutClicked: () -> Unit = {}
) {
    AnimatedVisibility(
        visible = isExpanded,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(SecondaryBarBg)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SecondaryMenuItem(icon = Icons.Rounded.Elderly, label = "Pension", onClick = onPensionClicked)
            SecondaryMenuItem(icon = Icons.Rounded.Calculate, label = stringResource(id = R.string.nav_gpf), onClick = onGpfClicked)
            SecondaryMenuItem(icon = Icons.Rounded.AccountBalanceWallet, label = stringResource(id = R.string.nav_salary_set), onClick = onSalarySetClicked)
            SecondaryMenuItem(icon = Icons.Rounded.VpnKey, label = stringResource(id = R.string.nav_api_key), onClick = onApiKeyClicked)
            SecondaryMenuItem(icon = Icons.Rounded.Settings, label = stringResource(id = R.string.nav_settings), onClick = onSettingsClicked)
            SecondaryMenuItem(icon = Icons.Rounded.Info, label = stringResource(id = R.string.nav_about), onClick = onAboutClicked)
        }
    }
}
