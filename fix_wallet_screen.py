with open('app/src/main/java/com/example/ui/screens/WalletScreen.kt', 'r') as f:
    content = f.read()

import re

# Remove TransactionRow function
transaction_row_func = """@Composable
fun TransactionRow(tx: TransactionEntity) {
    val isOut = tx.type == "OUT"
    val color = if (isOut) RedAccent else SuccessGreen
    val icon = if (isOut) Icons.Rounded.ArrowUpward else Icons.Rounded.ArrowDownward
    
    val dateString = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault()).format(Date(tx.date))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White.copy(alpha = 0.05f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(tx.medium.take(1).uppercase(), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(tx.medium, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(dateString, color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
            }
        }
        Text(
            text = "${if (isOut) "-" else "+"}৳${String.format("%.0f", tx.amount)}",
            color = color,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}"""

content = content.replace(transaction_row_func, "")

# Add import
import_stmt = "import com.example.ui.components.wallet.TransactionRow\n"
content = content.replace("import com.example.data.TransactionEntity", "import com.example.data.TransactionEntity\n" + import_stmt)

with open('app/src/main/java/com/example/ui/screens/WalletScreen.kt', 'w') as f:
    f.write(content)
