import re

with open('app/src/main/java/com/example/ui/components/salary/AllowanceCalculator.kt', 'r') as f:
    content = f.read()

# I will replace calculateHouseRent function
new_func = """private fun calculateHouseRent(profile: UserProfile, baseForAllowance: Long, locDhaka: String, locOtherCity: String): Long {
    // Unmarried condition: 30% everywhere
    if (profile.maritalStatus == "Unmarried") {
        return (baseForAllowance * 0.30).toLong()
    }

    // Married condition but Line Man or In-Living: 50% everywhere
    if (profile.isLineMan || profile.isInLiving) {
        val minFloor = when {
            baseForAllowance <= 9700 -> 4500L
            baseForAllowance <= 16000 -> 4800L
            baseForAllowance <= 35400 -> 7000L
            else -> 12600L
        }
        return (baseForAllowance * 0.50).toLong().coerceAtLeast(minFloor)
    }

    // Married, Family Man, Out-Living (Location Based)
    return when (profile.locationType) {
        locDhaka, "Dhaka Metropolitan", "ঢাকা মেট্রোপলিটন" -> {
            val rate = when {
                baseForAllowance <= 9700 -> 0.65
                baseForAllowance <= 16000 -> 0.60
                baseForAllowance <= 35400 -> 0.55
                else -> 0.50
            }
            val min = when {
                baseForAllowance <= 9700 -> 5600L
                baseForAllowance <= 16000 -> 6400L
                baseForAllowance <= 35400 -> 9600L
                else -> 19500L
            }
            (baseForAllowance * rate).toLong().coerceAtLeast(min)
        }
        locOtherCity, "Other City Corporation", "অন্যান্য সিটি কর্পোরেশন" -> {
            val rate = when {
                baseForAllowance <= 9700 -> 0.55
                baseForAllowance <= 16000 -> 0.50
                baseForAllowance <= 35400 -> 0.45
                else -> 0.40
            }
            val min = when {
                baseForAllowance <= 9700 -> 5000L
                baseForAllowance <= 16000 -> 5400L
                baseForAllowance <= 35400 -> 8000L
                else -> 16000L
            }
            (baseForAllowance * rate).toLong().coerceAtLeast(min)
        }
        else -> {
            val rate = when {
                baseForAllowance <= 9700 -> 0.50
                baseForAllowance <= 16000 -> 0.45
                baseForAllowance <= 35400 -> 0.40
                else -> 0.35
            }
            val min = when {
                baseForAllowance <= 9700 -> 4500L
                baseForAllowance <= 16000 -> 4800L
                baseForAllowance <= 35400 -> 7000L
                else -> 12600L
            }
            (baseForAllowance * rate).toLong().coerceAtLeast(min)
        }
    }
}"""

content = re.sub(r'private fun calculateHouseRent\(.*?^\}', new_func, content, flags=re.MULTILINE|re.DOTALL)

with open('app/src/main/java/com/example/ui/components/salary/AllowanceCalculator.kt', 'w') as f:
    f.write(content)
