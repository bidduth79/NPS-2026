package com.example.ui.components

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.UserProfileManager

@Composable
fun UserProfileDialog(onDismiss: () -> Unit, key: Long = 0L) {
    val context = LocalContext.current
    val userProfileManager = remember { UserProfileManager.getInstance(context) }
    val profile by userProfileManager.profile.collectAsState()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        UserProfileDialogContent(
            profile = profile,
            onSave = { updatedProfile ->
                userProfileManager.saveProfile(updatedProfile)
                onDismiss()
            },
            onDismiss = onDismiss
        )
    }
}
