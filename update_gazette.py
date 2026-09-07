import re

content = """package com.example.ui.components

import com.example.ui.theme.RedAccent
import com.example.ui.theme.ChipBg
import com.example.ui.theme.PureDark
import com.example.ui.theme.DarkCard
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleLight
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.PictureAsPdf
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BluePrimary

data class GazetteOption(
    val title: String,
    val resName: String,
    val fallbackResName: String? = null
)

@Composable
fun GazetteScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var selectedGazette by remember { mutableStateOf<GazetteOption?>(null) }

    val options = listOf(
        GazetteOption("Pay Scale-2015", "gazette_2015", "gazette"),
        GazetteOption("Pay Scale-2015 (BGB)", "gazette_2015_bgb"),
        GazetteOption("Pay Scale-2026", "gazette_2026"),
        GazetteOption("Pay Scale-2026(BGB)", "gazette_2026_bgb")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PureDark)
            .padding(bottom = 90.dp)
    ) {
        if (selectedGazette == null) {
            // Menu List
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Official Gazettes",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                options.forEach { option ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkCard),
                        shape = RoundedCornerShape(percent = 50), // Pillow box shape
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { selectedGazette = option }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 24.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = option.title,
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Icon(
                                imageVector = Icons.Rounded.PictureAsPdf,
                                contentDescription = "PDF",
                                tint = RedAccent,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        } else {
            // PDF Viewer Area
            Column(modifier = Modifier.fillMaxSize()) {
                // Header with Back Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkCard)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { selectedGazette = null }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = selectedGazette!!.title,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                var pdfResId = context.resources.getIdentifier(selectedGazette!!.resName, "raw", context.packageName)
                if (pdfResId == 0 && selectedGazette!!.fallbackResName != null) {
                    pdfResId = context.resources.getIdentifier(selectedGazette!!.fallbackResName, "raw", context.packageName)
                }

                if (pdfResId != 0) {
                    PdfViewer(resId = pdfResId)
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(ChipBg, RoundedCornerShape(24.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PictureAsPdf,
                                contentDescription = "PDF Not Found",
                                tint = RedAccent,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Text(
                            text = "PDF Not Available",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Please upload '${selectedGazette!!.resName}.pdf' into the 'app/src/main/res/raw' directory.",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp
                        )
                    }
                }
            }
        }
    }
}
"""

with open('app/src/main/java/com/example/ui/components/GazetteScreen.kt', 'w') as f:
    f.write(content)
