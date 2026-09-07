package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppDatabase
import com.example.data.TransactionEntity
import com.example.ui.components.wallet.TransactionRow

import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCard
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.RedAccent

@Composable
fun WalletScreen(modifier: Modifier = Modifier) {
    
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val transactionDao = db.transactionDao()
    val transactions by transactionDao.getAllTransactions().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    var hasSmsPermission by remember { 
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
        )
    }
    var showExplanationDialog by remember { mutableStateOf(!hasSmsPermission) }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        hasSmsPermission = permissions[Manifest.permission.RECEIVE_SMS] == true && permissions[Manifest.permission.READ_SMS] == true
    }

    if (showExplanationDialog && !hasSmsPermission) {
        AlertDialog(
            onDismissRequest = { showExplanationDialog = false },
            title = { Text("এসএমএস পারমিশন প্রয়োজন", fontWeight = FontWeight.Bold) },
            text = { Text("আপনার মাসিক আয়-ব্যয়ের স্বয়ংক্রিয় হিসাব রাখার জন্য, অ্যাপটি আপনার ব্যাংক, বিকাশ এবং নগদের ট্রানজেকশন মেসেজগুলো পড়ে সেগুলো সেভ করে রাখবে। আপনার কোনো ব্যক্তিগত তথ্য বা মেসেজ ইন্টারনেটে বা অন্য কোথাও শেয়ার করা হবে না, সবকিছু শুধুমাত্র আপনার ফোনেই সুরক্ষিত থাকবে।") },
            confirmButton = {
                Button(
                    onClick = {
                        showExplanationDialog = false
                        permissionLauncher.launch(arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS))
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                ) {
                    Text("সম্মত আছি", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExplanationDialog = false }) {
                    Text("এখন না", color = Color.White.copy(alpha = 0.6f))
                }
            },
            containerColor = DarkCard,
            titleContentColor = Color.White,
            textContentColor = Color.White.copy(alpha = 0.8f)
        )
    }


    val totalIn = transactions.filter { it.type == "IN" }.sumOf { it.amount }
    val totalOut = transactions.filter { it.type == "OUT" }.sumOf { it.amount }
    val balance = totalIn - totalOut

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // Top Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Monthly Overview", color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("৳ ${String.format("%.2f", balance)}", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // IN Card
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = DarkCard),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(36.dp).background(SuccessGreen.copy(alpha = 0.2f), CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Rounded.ArrowDownward, contentDescription = "In", tint = SuccessGreen)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Total IN", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                            Text("৳ ${String.format("%.0f", totalIn)}", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                // OUT Card
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = DarkCard),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(36.dp).background(RedAccent.copy(alpha = 0.2f), CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Rounded.ArrowUpward, contentDescription = "Out", tint = RedAccent)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Total OUT", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                            Text("৳ ${String.format("%.0f", totalOut)}", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Transaction List
        Card(
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(containerColor = DarkCard),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 24.dp)) {
                Text("Recent Transactions", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                
                if (transactions.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No transactions found yet. SMS tracking will show up here.", color = Color.White.copy(alpha = 0.5f), fontSize = 14.sp)
                    }
                } else {
                    LazyColumn {
                        items(transactions) { tx ->
                            TransactionRow(tx)
                            Divider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.padding(vertical = 12.dp))
                        }
                    }
                }
            }
        }
    }
}


