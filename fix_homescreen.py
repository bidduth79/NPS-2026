with open('app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

import re

# Pass callback to DeveloperSidebar
content = content.replace('DeveloperSidebar()', 'DeveloperSidebar(\n                onWalletClicked = { \n                    currentScreen = 5 \n                    scope.launch { drawerState.close() }\n                }\n            )')

# Add currentScreen == 5
content = content.replace('} else if (currentScreen == 4) {\n                    com.example.ui.components.pension.PensionScreen(onBack = { currentScreen = 0 })\n                }', '} else if (currentScreen == 4) {\n                    com.example.ui.components.pension.PensionScreen(onBack = { currentScreen = 0 })\n                } else if (currentScreen == 5) {\n                    WalletScreen()\n                }')

with open('app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)

