package com.example.ui.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DecorativeCircles() {
    Box(
        modifier = Modifier
            .offset(x = 200.dp, y = (-40).dp)
            .size(150.dp)
            .background(Color.White.copy(alpha = 0.1f), CircleShape)
    )
    Box(
        modifier = Modifier
            .offset(x = (-30).dp, y = 150.dp)
            .size(100.dp)
            .background(Color.White.copy(alpha = 0.1f), CircleShape)
    )
}
