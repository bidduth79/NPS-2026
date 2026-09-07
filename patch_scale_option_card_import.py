with open('app/src/main/java/com/example/ui/components/profile/ProfileHouseAllowanceSection.kt', 'r') as f:
    content = f.read()

if 'import com.example.ui.components.profile.ScaleOptionCard' not in content:
    content = content.replace(
        'import com.example.data.UserProfile',
        'import com.example.data.UserProfile\nimport com.example.ui.components.profile.ScaleOptionCard'
    )

with open('app/src/main/java/com/example/ui/components/profile/ProfileHouseAllowanceSection.kt', 'w') as f:
    f.write(content)
