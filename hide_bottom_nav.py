with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

import re

target = """            // Custom Navigation Bar fixed at the bottom
            androidx.compose.foundation.layout.Box(
                modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.BottomCenter
            ) {
                CustomBottomNavigation(scrollState = scrollState)
            }"""

replacement = """            // Custom Navigation Bar fixed at the bottom (Temporarily hidden)
            /*
            androidx.compose.foundation.layout.Box(
                modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.BottomCenter
            ) {
                CustomBottomNavigation(scrollState = scrollState)
            }
            */"""

content = content.replace(target, replacement)

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
