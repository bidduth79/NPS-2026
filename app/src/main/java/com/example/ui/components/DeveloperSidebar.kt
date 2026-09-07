package com.example.ui.components

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.ui.theme.DeepDarkCard
import com.example.ui.theme.DarkBorder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserProfileManager
import com.example.utils.AppPreferences
import com.example.utils.ThemeMode
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.DarkCard

@Composable
fun DeveloperSidebar(modifier: Modifier = Modifier, onWalletClicked: () -> Unit = {}) {
    val context = LocalContext.current
    val appPreferences = remember { AppPreferences(context) }
    val themeMode by appPreferences.themeMode.collectAsState(initial = ThemeMode.SYSTEM)
    val userProfileManager = remember { UserProfileManager.getInstance(context) }
    val profile by userProfileManager.profile.collectAsState()
    val scrollState = rememberScrollState()

    ModalDrawerSheet(
        modifier = modifier.width(300.dp),
        drawerContainerColor = DeepDarkCard,
        drawerContentColor = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ==========================================
            // FIXED TOP SECTION
            // ==========================================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(DarkBorder),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = com.example.R.drawable.developer_photo),
                        contentDescription = "Developer Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(text = "BIDDUTH", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Android Developer", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                Spacer(modifier = Modifier.height(24.dp))
                
                ContactRow(icon = Icons.Default.Email, text = "ctrkb79@gmail.com")
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                val intent = Intent(Intent.ACTION_DIAL).apply { data = Uri.parse("tel:01829300000") }
                                context.startActivity(intent)
                            }
                            .padding(vertical = 4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = "Call", tint = BluePrimary, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "01829300000", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                    }
                    
                    IconButton(
                        onClick = {
                            try {
                                val url = "https://api.whatsapp.com/send?phone=+8801829300000"
                                val intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) }
                                context.startActivity(intent)
                            } catch (e: ActivityNotFoundException) {
                                Toast.makeText(context, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(imageVector = Icons.Rounded.Chat, contentDescription = "WhatsApp", tint = Color(0xFF25D366))
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                
                SidebarSettingsCard(themeMode, appPreferences, profile, userProfileManager)
            }
            
            Divider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp))

            // ==========================================
            // SCROLLABLE BOTTOM SECTION
            // ==========================================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkCard),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onWalletClicked() }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color(0xFF2E7D32).copy(alpha = 0.2f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Rounded.AccountBalanceWallet, contentDescription = "Wallet", tint = Color(0xFF81C784), modifier = Modifier.size(24.dp))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = "Bank Wallet", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                Text(text = "SMS Tracker (Beta)", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                            }
                        }
                        Icon(imageVector = Icons.Rounded.ChevronRight, contentDescription = "Go", tint = Color.White.copy(alpha = 0.5f))
                    }
                }
            }
        }
    }
}

@Composable
fun ContactRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Icon(imageVector = icon, contentDescription = null, tint = BluePrimary, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
    }
}
