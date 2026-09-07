import re

with open('app/src/main/java/com/example/ui/components/ActionButtons.kt', 'r') as f:
    content = f.read()

# Add imports
imports = """import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext"""

content = content.replace("import androidx.compose.runtime.Composable", imports)

# Update component
old_comp = """@Composable
fun ActionButtons(result: CalculationResult) {
    val context = LocalContext.current"""

new_comp = """@Composable
fun ActionButtons(result: CalculationResult) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isGeneratingPdf by remember { mutableStateOf(false) }"""

content = content.replace(old_comp, new_comp)

# Update button
old_button = """        OutlinedButton(
            onClick = { PdfGenerator.generateAndSavePdf(context, result) },
            modifier = Modifier.weight(1f).height(52.dp),"""

new_button = """        OutlinedButton(
            onClick = { 
                if (!isGeneratingPdf) {
                    isGeneratingPdf = true
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            PdfGenerator.generateAndSavePdf(context, result)
                        }
                        isGeneratingPdf = false
                    }
                }
            },
            modifier = Modifier.weight(1f).height(52.dp),"""

content = content.replace(old_button, new_button)

# Update icon
old_icon = """            Icon(
                imageVector = Icons.Default.PictureAsPdf,
                contentDescription = "PDF",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "PDF",
                fontWeight = FontWeight.Bold
            )"""

new_icon = """            if (isGeneratingPdf) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color(0xFFC77DFF),
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "অপেক্ষা করুন...", fontWeight = FontWeight.Bold)
            } else {
                Icon(
                    imageVector = Icons.Default.PictureAsPdf,
                    contentDescription = "PDF",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "PDF",
                    fontWeight = FontWeight.Bold
                )
            }"""

content = content.replace(old_icon, new_icon)

with open('app/src/main/java/com/example/ui/components/ActionButtons.kt', 'w') as f:
    f.write(content)
