with open('app/src/main/java/com/example/ui/components/profile/ProfileHouseAllowanceSection.kt', 'r') as f:
    content = f.read()

target = '''@Composable
fun ProfileHouseAllowanceSection(profile: UserProfile, onProfileChange: (UserProfile) -> Unit) {'''

replacement = '''@Composable
fun ProfileHouseAllowanceSection(profile: UserProfile, onProfileChange: (UserProfile) -> Unit) {'''

if 'import com.example.ui.components.profile.ScaleOptionCard' not in content:
    # ScaleOptionCard is inside ProfileBaseScaleSection.kt but I need to make sure we can import it
    pass

with open('app/src/main/java/com/example/ui/components/profile/ProfileHouseAllowanceSection.kt', 'w') as f:
    f.write(content)
