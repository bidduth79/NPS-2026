with open('app/src/main/java/com/example/data/UserProfileManager.kt', 'r') as f:
    content = f.read()

# Add appLanguage to UserProfile data class
new_fields = """    val awardAllowanceAmount: Long = 0L,
    val appLanguage: String = "en"
"""
content = content.replace('    val awardAllowanceAmount: Long = 0L\n', new_fields)

# Add load logic
load_logic = """            recreationAllowanceAmount = prefs.getLong("recreation_allowance_amount", 0L),
            awardAllowanceAmount = prefs.getLong("award_allowance_amount", 0L),
            appLanguage = prefs.getString("app_language", "en") ?: "en"
"""
content = content.replace('            recreationAllowanceAmount = prefs.getLong("recreation_allowance_amount", 0L),\n            awardAllowanceAmount = prefs.getLong("award_allowance_amount", 0L)\n', load_logic)

# Add save logic
save_logic = """            putLong("recreation_allowance_amount", newProfile.recreationAllowanceAmount)
            putLong("award_allowance_amount", newProfile.awardAllowanceAmount)
            putString("app_language", newProfile.appLanguage)
"""
content = content.replace('            putLong("recreation_allowance_amount", newProfile.recreationAllowanceAmount)\n            putLong("award_allowance_amount", newProfile.awardAllowanceAmount)\n', save_logic)

with open('app/src/main/java/com/example/data/UserProfileManager.kt', 'w') as f:
    f.write(content)

