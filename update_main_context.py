import re

with open('app/src/main/java/com/example/MainActivity.kt', 'r') as f:
    content = f.read()

attach_base = """
    override fun attachBaseContext(newBase: android.content.Context) {
        val prefs = newBase.getSharedPreferences("user_profile", android.content.Context.MODE_PRIVATE)
        val lang = prefs.getString("app_language", "en") ?: "en"
        val locale = java.util.Locale(lang)
        java.util.Locale.setDefault(locale)
        val config = android.content.res.Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
"""
content = content.replace('    override fun onCreate(savedInstanceState: Bundle?) {', attach_base)

with open('app/src/main/java/com/example/MainActivity.kt', 'w') as f:
    f.write(content)
