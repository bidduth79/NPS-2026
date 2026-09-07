import re

with open('app/src/main/java/com/example/ui/theme/Color.kt', 'r') as f:
    content = f.read()

# Make sure we import LocalDarkTheme and Composable
if 'LocalDarkTheme' not in content:
    content = content.replace('import androidx.compose.ui.graphics.Color',
                              'import androidx.compose.ui.graphics.Color\nimport androidx.compose.runtime.Composable\nimport com.example.ui.theme.LocalDarkTheme')

replacements = {
    r'val DarkBackground = Color\(0xFF120F16\)': r'val DarkBackground: Color\n    @Composable get() = if (LocalDarkTheme.current) Color(0xFF120F16) else BackgroundLight',
    r'val PureDark = Color\(0xFF0F0F11\)': r'val PureDark: Color\n    @Composable get() = if (LocalDarkTheme.current) Color(0xFF0F0F11) else Color(0xFFF1F5F9)',
    r'val DarkCard = Color\(0xFF1C1924\)': r'val DarkCard: Color\n    @Composable get() = if (LocalDarkTheme.current) Color(0xFF1C1924) else Color.White',
    r'val DarkCardInner = Color\(0xFF262130\)': r'val DarkCardInner: Color\n    @Composable get() = if (LocalDarkTheme.current) Color(0xFF262130) else Color(0xFFF8FAFC)',
    r'val DarkCardAlternative = Color\(0xFF252131\)': r'val DarkCardAlternative: Color\n    @Composable get() = if (LocalDarkTheme.current) Color(0xFF252131) else Color(0xFFF1F5F9)',
    r'val DarkDialogBg = Color\(0xE01C1924\)': r'val DarkDialogBg: Color\n    @Composable get() = if (LocalDarkTheme.current) Color(0xE01C1924) else Color(0xE0FFFFFF)',
    r'val DarkBorder = Color\(0xFF3B3247\)': r'val DarkBorder: Color\n    @Composable get() = if (LocalDarkTheme.current) Color(0xFF3B3247) else Color(0xFFE2E8F0)',
    r'val ChipBg = Color\(0xFF2C2C2E\)': r'val ChipBg: Color\n    @Composable get() = if (LocalDarkTheme.current) Color(0xFF2C2C2E) else Color(0xFFF1F5F9)',
    r'val SecondaryBarBg = Color\(0xFF161619\)': r'val SecondaryBarBg: Color\n    @Composable get() = if (LocalDarkTheme.current) Color(0xFF161619) else Color(0xFFF8FAFC)',
    r'val DeepDarkCard = Color\(0xFF1C1326\)': r'val DeepDarkCard: Color\n    @Composable get() = if (LocalDarkTheme.current) Color(0xFF1C1326) else Color.White',
    r'val TextPrimaryDark = Color\(0xFFFFFFFF\)': r'val TextPrimaryDark: Color\n    @Composable get() = if (LocalDarkTheme.current) Color(0xFFFFFFFF) else TextPrimary',
    r'val TextSecondaryDark = Color.Gray': r'val TextSecondaryDark: Color\n    @Composable get() = if (LocalDarkTheme.current) Color.Gray else TextSecondary',
    r'val TextGray = Color\(0xFF9CA3AF\)': r'val TextGray: Color\n    @Composable get() = if (LocalDarkTheme.current) Color(0xFF9CA3AF) else Color(0xFF64748B)'
}

for old, new in replacements.items():
    content = re.sub(old, new, content)

with open('app/src/main/java/com/example/ui/theme/Color.kt', 'w') as f:
    f.write(content)

