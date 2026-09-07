with open('app/src/main/java/com/example/ui/components/pension/PensionScreen.kt', 'r') as f:
    content = f.read()

import re

# Match the entire card from "Card(" down to the end of the input section
card_code = """            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Basic Salary (শেষ মূল বেতন)",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = basicSalaryStr,
                        onValueChange = { if (it.all { char -> char.isDigit() }) basicSalaryStr = it },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentPurple,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = AccentPurple
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Years of Service (চাকরির বয়স)",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "${yearsOfService.toInt()} Years",
                            color = AccentPurpleLight,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Slider(
                        value = yearsOfService,
                        onValueChange = { yearsOfService = it },
                        valueRange = 10f..40f,
                        steps = 29, // (40 - 10) - 1
                        colors = SliderDefaults.colors(
                            thumbColor = AccentPurpleLight,
                            activeTrackColor = AccentPurple,
                            inactiveTrackColor = Color.White.copy(alpha = 0.1f)
                        )
                    )
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
                }
            }"""

replacement = """            PensionInputCard(
                basicSalaryStr = basicSalaryStr,
                onBasicSalaryChange = { basicSalaryStr = it },
                yearsOfService = yearsOfService,
                onYearsOfServiceChange = { yearsOfService = it },
                accruedLeaveMonths = accruedLeaveMonths,
                onAccruedLeaveMonthsChange = { accruedLeaveMonths = it }
            )"""

content = content.replace(card_code, replacement)

with open('app/src/main/java/com/example/ui/components/pension/PensionScreen.kt', 'w') as f:
    f.write(content)

