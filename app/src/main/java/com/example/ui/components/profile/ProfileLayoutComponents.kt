package com.example.ui.components.profile

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.AccentPurpleLight

import com.example.ui.theme.AccentPurple

import com.example.ui.theme.DarkCard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CardBg
    @androidx.compose.runtime.Composable get() = DarkCard
private val IconBg = AccentPurple.copy(alpha = 0.2f)
private val IconTint = AccentPurpleLight
private val TextPrimary
    @androidx.compose.runtime.Composable get() = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
private val TextSecondary
    @androidx.compose.runtime.Composable get() = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant

@Composable
fun ProfileDialogBackground(scrollState: ScrollState) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                translationY = -(scrollState.value * 0.4f)
            }
    ) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(AccentPurple.copy(alpha = 0.15f), Color.Transparent),
                center = Offset(size.width * 0.9f, size.height * 0.1f),
                radius = size.width * 0.8f
            )
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(AccentPurpleLight.copy(alpha = 0.1f), Color.Transparent),
                center = Offset(size.width * 0.1f, size.height * 0.5f),
                radius = size.width * 0.7f
            )
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF7B2CBF).copy(alpha = 0.15f), Color.Transparent),
                center = Offset(size.width * 0.8f, size.height * 0.9f),
                radius = size.width * 0.9f
            )
        )
    }
}

@Composable
fun ProfileDialogHeader(onDismiss: () -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(IconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AccountBalanceWallet,
                        contentDescription = "Profile",
                        tint = IconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = "Salary Settings",
                        color = TextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Manage your payroll rules",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(CardBg)
            ) {
                Icon(
                    Icons.Rounded.Close,
                    contentDescription = "Close",
                    tint = TextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun ProfileSaveButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(start = 16.dp, end = 16.dp, bottom = 28.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentPurple),
            shape = RoundedCornerShape(14.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Icon(
                Icons.Rounded.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                stringResource(id = R.string.action_save_profile),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
