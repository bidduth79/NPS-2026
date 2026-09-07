with open('app/src/main/java/com/example/ui/screens/WalletScreen.kt', 'r') as f:
    content = f.read()

import re

# Add imports
imports = """import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
"""
content = content.replace('import androidx.compose.foundation.background\n', imports + 'import androidx.compose.foundation.background\n')

# Inside WalletScreen
permission_logic = """
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
"""

content = re.sub(
    r'val context = LocalContext\.current.*?val scope = rememberCoroutineScope\(\)',
    permission_logic,
    content,
    flags=re.DOTALL
)

with open('app/src/main/java/com/example/ui/screens/WalletScreen.kt', 'w') as f:
    f.write(content)

