with open('app/src/main/java/com/example/ui/components/pension/PensionScreen.kt', 'r') as f:
    content = f.read()

import re
top_logic = """
    // Pension Calculation Logic (National Pay Scale 2015 BD)
    val percentage = when (yearsOfService.toInt()) {
        in 0..4 -> 0.0
        in 5..9 -> 0.0 // Simplified: standard pension starts at 10
        10 -> 0.51
        11 -> 0.54
        12 -> 0.57
        13 -> 0.60
        14 -> 0.63
        15 -> 0.65
        16 -> 0.69
        17 -> 0.72
        18 -> 0.75
        19 -> 0.78
        20 -> 0.81
        21 -> 0.84
        22 -> 0.87
        23, 24 -> 0.90
        else -> 0.90 // 25+ years
    }

    val gratuityRate = when (yearsOfService.toInt()) {
        in 0..9 -> 0L
        in 10..14 -> 260L
        in 15..19 -> 245L
        else -> 230L // 20+ years
    }

    val grossPension = (basicSalary * percentage).toLong()
    val surrendered = grossPension / 2
    val retained = grossPension - surrendered 

    val lumpSumGratuity = surrendered * gratuityRate
    val leaveEncashment = basicSalary * 18 // ছুটি নগদায়ন (সর্বোচ্চ ১৮ মাস)
    val totalLumpSum = lumpSumGratuity + leaveEncashment

    val medicalAllowance = 1500L
    val monthlyPension = retained + medicalAllowance
"""

content = re.sub(r'// Pension Calculation Logic.*?val monthlyPension = retained \+ medicalAllowance', top_logic.strip(), content, flags=re.DOTALL)

with open('app/src/main/java/com/example/ui/components/pension/PensionScreen.kt', 'w') as f:
    f.write(content)
