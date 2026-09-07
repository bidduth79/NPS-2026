import re

with open('app/src/main/java/com/example/utils/PdfGenerator.kt', 'r') as f:
    content = f.read()

# Replace imports
old_imports = """import android.widget.Toast
import java.io.File"""

new_imports = """import android.widget.Toast
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch"""

if "kotlinx.coroutines.launch" not in content:
    content = content.replace("import android.widget.Toast", new_imports)

old_success = """                document.writeTo(it)
                Toast.makeText(context, "PDF saved to Downloads", Toast.LENGTH_LONG).show()"""

new_success = """                document.writeTo(it)
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "PDF saved to Downloads", Toast.LENGTH_LONG).show()
                }"""

old_fail = """        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to save PDF", Toast.LENGTH_SHORT).show()
        }"""

new_fail = """        } catch (e: Exception) {
            e.printStackTrace()
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(context, "Failed to save PDF", Toast.LENGTH_SHORT).show()
            }
        }"""

content = content.replace(old_success, new_success)
content = content.replace(old_fail, new_fail)

with open('app/src/main/java/com/example/utils/PdfGenerator.kt', 'w') as f:
    f.write(content)
