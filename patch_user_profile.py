with open('app/src/main/java/com/example/data/UserProfileManager.kt', 'r') as f:
    content = f.read()

# Add maritalStatus field
if 'val maritalStatus: String = "Unmarried"' not in content:
    content = content.replace(
        'val isFamilyMan: Boolean = true,',
        'val maritalStatus: String = "Unmarried",\n    val isLineMan: Boolean = false,\n    val isFamilyMan: Boolean = true,'
    )
    
    content = content.replace(
        'isFamilyMan = prefs.getBoolean("is_family_man", true),',
        'maritalStatus = prefs.getString("marital_status", "Unmarried") ?: "Unmarried",\n            isLineMan = prefs.getBoolean("is_line_man", false),\n            isFamilyMan = prefs.getBoolean("is_family_man", true),'
    )
    
    content = content.replace(
        'putBoolean("is_family_man", newProfile.isFamilyMan)',
        'putString("marital_status", newProfile.maritalStatus)\n            putBoolean("is_line_man", newProfile.isLineMan)\n            putBoolean("is_family_man", newProfile.isFamilyMan)'
    )

with open('app/src/main/java/com/example/data/UserProfileManager.kt', 'w') as f:
    f.write(content)
