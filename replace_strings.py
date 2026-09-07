import os
import re

string_map = {
    '"Location & Regional"': 'stringResource(id = R.string.profile_location_regional)',
    '"House Rent"': 'stringResource(id = R.string.profile_house_rent)',
    '"Posting Area (House Rent)"': 'stringResource(id = R.string.profile_posting_area)',
    '"Dhaka Metropolitan"': 'stringResource(id = R.string.loc_dhaka)',
    '"Other City Corporation"': 'stringResource(id = R.string.loc_other_city)',
    '"Other Locations"': 'stringResource(id = R.string.loc_other)',
    '"Frontier Allowance"': 'stringResource(id = R.string.profile_frontier_allowance)',
    '"Enable"': 'stringResource(id = R.string.action_enable)',
    '"Hill Allowance (%)"': 'stringResource(id = R.string.profile_hill_allowance)',
    '"Joining Date"': 'stringResource(id = R.string.profile_joining_date)',
    '"DD/MM/YYYY"': 'stringResource(id = R.string.date_format_placeholder)',
    '"Children & Education"': 'stringResource(id = R.string.profile_children_education)',
    '"Allowance"': 'stringResource(id = R.string.profile_allowance)',
    '"Number of Children"': 'stringResource(id = R.string.profile_num_children)',
    '"Disabled Children"': 'stringResource(id = R.string.profile_disabled_children)',
    '"Other Allowances"': 'stringResource(id = R.string.profile_other_allowances)',
    '"Tiffin Allowance"': 'stringResource(id = R.string.profile_tiffin_allowance)',
    '"Washing Allowance"': 'stringResource(id = R.string.profile_washing_allowance)',
    '"Mobile Bill Amount"': 'stringResource(id = R.string.profile_mobile_bill)',
    '"Trade/Special Allowance"': 'stringResource(id = R.string.profile_trade_allowance)',
    '"Deductions"': 'stringResource(id = R.string.profile_deductions)',
    '"GPF Deduction Amount"': 'stringResource(id = R.string.profile_gpf_deduction)',
    '"Base Scale"': 'stringResource(id = R.string.profile_base_scale)',
    '"Current basic scale"': 'stringResource(id = R.string.profile_current_basic_scale)',
    '"Save Profile"': 'stringResource(id = R.string.action_save_profile)',
    
    '"AI Assistant"': 'stringResource(id = R.string.chat_ai_assistant)',
    '"Clear History"': 'stringResource(id = R.string.chat_clear_history)',
    '"Pay Scale"': 'stringResource(id = R.string.chat_chip_pay_scale)',
    '"Pension"': 'stringResource(id = R.string.chat_chip_pension)',
    '"Festival Allowance"': 'stringResource(id = R.string.chat_chip_festival)',
    '"Hello! I am AI Assistant. Ask me anything."': 'stringResource(id = R.string.chat_greeting)',
    '"Ask me anything..."': 'stringResource(id = R.string.chat_placeholder)',
    
    '"Old Basic"': 'stringResource(id = R.string.chart_old_basic)',
    '"Stage 1"': 'stringResource(id = R.string.chart_stage_1)',
    '"Stage 2"': 'stringResource(id = R.string.chart_stage_2)',
    '"Stage 3"': 'stringResource(id = R.string.chart_stage_3)',
    '"Final"': 'stringResource(id = R.string.chart_final)',
    
    '"Gross Salary"': 'stringResource(id = R.string.salary_gross)',
    '"Total Deductions"': 'stringResource(id = R.string.salary_total_deductions)',
    '"Net Salary"': 'stringResource(id = R.string.salary_net)',
    '"Basic Salary"': 'stringResource(id = R.string.salary_basic)',
    '"Medical Allowance"': 'stringResource(id = R.string.salary_medical)',
    '"House Rent Allowance"': 'stringResource(id = R.string.salary_house_rent_allowance)',
    '"Education Allowance"': 'stringResource(id = R.string.salary_education_allowance)',
    '"Total Additional Allowances"': 'stringResource(id = R.string.salary_total_additional)',
    '"Basic after adjustment"': 'stringResource(id = R.string.salary_basic_after_adjustment)'
}

# Add conditional replacement for places like "Dhaka", "City" which shouldn't be touched by simple string replace unless they match exactly
# Actually, wait, some are matched above

base_dir = "app/src/main/java/com/example/ui"

for root, _, files in os.walk(base_dir):
    for file in files:
        if file.endswith(".kt"):
            filepath = os.path.join(root, file)
            with open(filepath, 'r') as f:
                content = f.read()
            
            original_content = content
            for old_str, new_str in string_map.items():
                # We need to make sure we are not replacing substrings inside other words.
                # Since we are replacing exact string literals like "Stage 1", the quotes help isolate it.
                content = content.replace(old_str, new_str)
            
            if content != original_content:
                # Add imports if missing
                if "import androidx.compose.ui.res.stringResource" not in content:
                    content = re.sub(r'^(package .*)$', rf'\1\n\nimport androidx.compose.ui.res.stringResource\nimport com.example.R', content, count=1, flags=re.MULTILINE)
                            
                with open(filepath, 'w') as f:
                    f.write(content)

print("Done replacing strings")
