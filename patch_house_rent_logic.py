with open('app/src/main/java/com/example/utils/SalaryBreakdownHelper.kt', 'r') as f:
    content = f.read()

target_logic = '''    val houseRentRate = when (profile.locationType) {
        "Dhaka Metropolitan", "ঢাকা মেট্রোপলিটন" -> if (baseForAllowance <= 9700) 0.65 else if (baseForAllowance <= 16000) 0.60 else if (baseForAllowance <= 35400) 0.55 else 0.50
        "Other City Corporation", "অন্যান্য সিটি কর্পোরেশন" -> if (baseForAllowance <= 9700) 0.55 else if (baseForAllowance <= 16000) 0.50 else if (baseForAllowance <= 35400) 0.45 else 0.40
        else -> if (baseForAllowance <= 9700) 0.50 else if (baseForAllowance <= 16000) 0.45 else if (baseForAllowance <= 35400) 0.40 else 0.35
    }

    val minimumHouseRent = when (profile.locationType) {
        "Dhaka Metropolitan", "ঢাকা মেট্রোপলিটন" -> if (baseForAllowance <= 9700) 5600L else if (baseForAllowance <= 16000) 6400L else if (baseForAllowance <= 35400) 9600L else 19500L
        "Other City Corporation", "অন্যান্য সিটি কর্পোরেশন" -> if (baseForAllowance <= 9700) 5000L else if (baseForAllowance <= 16000) 5400L else if (baseForAllowance <= 35400) 8000L else 16000L
        else -> if (baseForAllowance <= 9700) 4500L else if (baseForAllowance <= 16000) 4800L else if (baseForAllowance <= 35400) 7000L else 12600L
    }

    val houseRent = (baseForAllowance * houseRentRate).toLong().coerceAtLeast(minimumHouseRent)'''

replacement_logic = '''    val houseRentRate = when {
        profile.maritalStatus == "Unmarried" -> 0.30
        profile.maritalStatus == "Married" && profile.isLineMan -> 0.50
        profile.maritalStatus == "Married" && profile.isFamilyMan && profile.isInLiving -> 0.50
        else -> when (profile.locationType) { // Married + Family Man + Out Living
            "Dhaka Metropolitan", "ঢাকা মেট্রোপলিটন" -> if (baseForAllowance <= 9700) 0.65 else if (baseForAllowance <= 16000) 0.60 else if (baseForAllowance <= 35400) 0.55 else 0.50
            "Other City Corporation", "অন্যান্য সিটি কর্পোরেশন" -> if (baseForAllowance <= 9700) 0.55 else if (baseForAllowance <= 16000) 0.50 else if (baseForAllowance <= 35400) 0.45 else 0.40
            else -> if (baseForAllowance <= 9700) 0.50 else if (baseForAllowance <= 16000) 0.45 else if (baseForAllowance <= 35400) 0.40 else 0.35
        }
    }

    val minimumHouseRent = when {
        profile.maritalStatus == "Unmarried" -> 0L
        profile.maritalStatus == "Married" && profile.isLineMan -> 0L
        profile.maritalStatus == "Married" && profile.isFamilyMan && profile.isInLiving -> 0L
        else -> when (profile.locationType) {
            "Dhaka Metropolitan", "ঢাকা মেট্রোপলিটন" -> if (baseForAllowance <= 9700) 5600L else if (baseForAllowance <= 16000) 6400L else if (baseForAllowance <= 35400) 9600L else 19500L
            "Other City Corporation", "অন্যান্য সিটি কর্পোরেশন" -> if (baseForAllowance <= 9700) 5000L else if (baseForAllowance <= 16000) 5400L else if (baseForAllowance <= 35400) 8000L else 16000L
            else -> if (baseForAllowance <= 9700) 4500L else if (baseForAllowance <= 16000) 4800L else if (baseForAllowance <= 35400) 7000L else 12600L
        }
    }

    val houseRent = (baseForAllowance * houseRentRate).toLong().coerceAtLeast(minimumHouseRent)'''

content = content.replace(target_logic, replacement_logic)

with open('app/src/main/java/com/example/utils/SalaryBreakdownHelper.kt', 'w') as f:
    f.write(content)
