with open('app/src/main/java/com/example/ui/components/pension/PensionScreen.kt', 'r') as f:
    content = f.read()

import re

# Add state for accrued leave
state_decl = """
    var basicSalaryStr by remember { mutableStateOf("45000") }
    var yearsOfService by remember { mutableFloatStateOf(25f) }
    var accruedLeaveMonths by remember { mutableFloatStateOf(18f) }
"""
content = re.sub(r'var basicSalaryStr by remember.*?var yearsOfService by remember \{ mutableFloatStateOf\(25f\) \}', state_decl.strip(), content, flags=re.DOTALL)

# Add Slider for accrued leave
slider_ui = """
                    Text(
                        "Note: Minimum 10 years required for monthly pension.",
                        color = TextGray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Unused Leave (অর্জিত ছুটি)",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "${accruedLeaveMonths.toInt()} Months",
                            color = AccentPurpleLight,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Slider(
                        value = accruedLeaveMonths,
                        onValueChange = { accruedLeaveMonths = it },
                        valueRange = 0f..18f,
                        steps = 17, // (18 - 0) - 1
                        colors = SliderDefaults.colors(
                            thumbColor = AccentPurpleLight,
                            activeTrackColor = AccentPurple,
                            inactiveTrackColor = Color.White.copy(alpha = 0.1f)
                        )
                    )
                    Text(
                        "Note: Max 18 months allowable for encashment.",
                        color = TextGray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
"""
content = content.replace('Text(\n                        "Note: Minimum 10 years required for monthly pension.",\n                        color = TextGray,\n                        fontSize = 12.sp,\n                        modifier = Modifier.padding(top = 4.dp)\n                    )', slider_ui.strip())

# Update Calculation Logic
calc_old = 'val leaveEncashment = basicSalary * 18 // ছুটি নগদায়ন (সর্বোচ্চ ১৮ মাস)'
calc_new = 'val leaveEncashment = basicSalary * accruedLeaveMonths.toLong() // ছুটি নগদায়ন'
content = content.replace(calc_old, calc_new)

# Update Result Text
text_old = 'Text("Leave Encashment / ছুটি নগদায়ন (১৮ মাস)", color = TextGray, fontSize = 14.sp)'
text_new = 'Text("Leave Encashment / ছুটি নগদায়ন (${accruedLeaveMonths.toInt()} মাস)", color = TextGray, fontSize = 14.sp)'
content = content.replace(text_old, text_new)


with open('app/src/main/java/com/example/ui/components/pension/PensionScreen.kt', 'w') as f:
    f.write(content)
