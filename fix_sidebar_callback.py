with open('app/src/main/java/com/example/ui/components/DeveloperSidebar.kt', 'r') as f:
    content = f.read()

import re

# Add onWalletClicked parameter
content = content.replace('fun DeveloperSidebar(modifier: Modifier = Modifier)', 'fun DeveloperSidebar(modifier: Modifier = Modifier, onWalletClicked: () -> Unit = {})')

# Replace the clickable Toast with the callback
content = content.replace('.clickable { Toast.makeText(context, "Wallet Tracker coming soon...", Toast.LENGTH_SHORT).show() }', '.clickable { onWalletClicked() }')

with open('app/src/main/java/com/example/ui/components/DeveloperSidebar.kt', 'w') as f:
    f.write(content)

