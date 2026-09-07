with open('app/src/main/java/com/example/ui/components/SalaryScreen.kt', 'r') as f:
    content = f.read()

import re

new_content = """        // Result Display
        if (selectedGrade != null && finalBasicAmount != null) {
            val basic = finalBasicAmount
            
            val locDhaka = stringResource(id = R.string.loc_dhaka)
            val locOtherCity = stringResource(id = R.string.loc_other_city)
            val allowances = com.example.ui.components.salary.calculateDetailedAllowances(basic, selectedGrade, profile, selectedStepIndex, locDhaka, locOtherCity)
            
            com.example.ui.components.salary.DetailedCalculationCard(basic, profile, allowances)
            
            com.example.ui.components.salary.FutureIncrementForecast(
                grade = selectedGrade,
                currentStepIndex = selectedStepIndex,
                isScale2026 = isScale2026
            )
        }
    }
}"""

content = re.sub(r'        // Result Display.*?com\.example\.ui\.components\.salary\.DetailedCalculationCard\(basic, profile, allowances\)\s*\}\s*\}\s*\}', new_content.strip(), content, flags=re.DOTALL)

with open('app/src/main/java/com/example/ui/components/SalaryScreen.kt', 'w') as f:
    f.write(content)
