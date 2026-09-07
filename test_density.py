with open('app/src/main/java/com/example/MainActivity.kt', 'r') as f:
    content = f.read()

import re
new_theme = """            val userProfileManager = remember { com.example.data.UserProfileManager.getInstance(context) }
            val profile by userProfileManager.profile.collectAsState()
            
            AppTheme(darkTheme = darkTheme) {
                val currentDensity = LocalDensity.current
                val customDensity = if (profile.appLanguage == "bn") {
                    androidx.compose.ui.unit.Density(currentDensity.density, currentDensity.fontScale * 0.9f)
                } else currentDensity
                
                CompositionLocalProvider(LocalDensity provides customDensity) {
                    TouchSweepOverlay {
                        com.example.ui.screens.HomeScreen()
                    }
                }
            }"""

content = re.sub(r'            AppTheme\(darkTheme = darkTheme\) \{.*?com\.example\.ui\.screens\.HomeScreen\(\)\s*\}\s*\}', new_theme, content, flags=re.DOTALL)

with open('app/src/main/java/com/example/MainActivity.kt', 'w') as f:
    f.write(content)
