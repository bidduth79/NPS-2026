package com.example.ui.components.gpf

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.BlueAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GpfHeader(onBack: () -> Unit, onDownloadClick: () -> Unit) {
    TopAppBar(
        title = { Text("GPF Calculator", color = Color.White) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        },
        actions = {
            IconButton(onClick = onDownloadClick) {
                Icon(Icons.Default.PictureAsPdf, contentDescription = "Download PDF", tint = BlueAccent)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}
