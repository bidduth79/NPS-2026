

package com.example.ui.components.navigation

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.PureDark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Article
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainBottomBar(
    selectedItem: Int,
    isMoreExpanded: Boolean,
    onItemSelected: (Int) -> Unit,
    onMoreClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(PureDark)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavMenuItem(
                icon = Icons.Rounded.Home, 
                label = stringResource(id = R.string.nav_home), 
                isSelected = selectedItem == 0,
                onClick = { onItemSelected(0) }
            )
            NavMenuItem(
                icon = Icons.AutoMirrored.Rounded.Article, 
                label = stringResource(id = R.string.nav_gazette), 
                isSelected = selectedItem == 1,
                onClick = { onItemSelected(1) }
            )
            
            Spacer(modifier = Modifier.width(72.dp))
            
            NavMenuItem(
                icon = Icons.Rounded.AccountBalanceWallet, 
                label = stringResource(id = R.string.nav_salary), 
                isSelected = selectedItem == 2,
                onClick = { onItemSelected(2) }
            )
            NavMenuItem(
                icon = Icons.Rounded.MoreHoriz, 
                label = stringResource(id = R.string.nav_more), 
                isSelected = isMoreExpanded || selectedItem == 3,
                onClick = onMoreClicked
            )
        }
    }
}
