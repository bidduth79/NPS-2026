with open('app/src/main/java/com/example/ui/components/ThemeToggle.kt', 'r') as f:
    content = f.read()

content = content.replace('import com.example.utils.ThemePreferences', 'import com.example.utils.AppPreferences')
content = content.replace('ThemePreferences(context)', 'AppPreferences(context)')

with open('app/src/main/java/com/example/ui/components/ThemeToggle.kt', 'w') as f:
    f.write(content)

