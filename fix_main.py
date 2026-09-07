with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

# Fix the trailing braces properly
# Remove the CustomBottomNavigation temporarily, we'll insert it correctly

content = content.replace("CustomBottomNavigation(\n                    scrollState = scrollState,\n                    modifier = Modifier.align(Alignment.BottomCenter)\n                )", "CustomBottomNavigation(scrollState = scrollState, modifier = androidx.compose.ui.Modifier.align(androidx.compose.ui.Alignment.BottomCenter))")

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
