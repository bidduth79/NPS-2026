with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

content = content.replace("CustomBottomNavigation(scrollState = scrollState, modifier = androidx.compose.ui.Modifier.align(androidx.compose.ui.Alignment.BottomCenter))", "androidx.compose.foundation.layout.Box(modifier = androidx.compose.ui.Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.BottomCenter) {\n                    CustomBottomNavigation(scrollState = scrollState)\n                }")

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
