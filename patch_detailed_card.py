with open('app/src/main/java/com/example/ui/components/salary/DetailedCalculationCard.kt', 'r') as f:
    content = f.read()

header_target = '''        Column(modifier = Modifier.padding(16.dp)) {
            Text(stringResource(id = R.string.calculation_details), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))'''

header_replacement = '''        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(id = R.string.calculation_details), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                
                val context = androidx.compose.ui.platform.LocalContext.current
                val launcher = androidx.compose.activity.compose.rememberLauncherForActivityResult(
                    androidx.activity.result.contract.ActivityResultContracts.CreateDocument("application/pdf")
                ) { uri ->
                    if (uri != null) {
                        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                            com.example.utils.PdfGenerator.generateSalaryStatement(
                                context = context,
                                outputStream = outputStream,
                                basic = basic,
                                profile = profile,
                                allowances = allowances,
                                gradeIndex = profile.selectedGradeIndex,
                                stepIndex = profile.selectedStepIndex,
                                stageName = profile.selectedStageName
                            )
                            android.widget.Toast.makeText(context, "PDF Saved", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                
                androidx.compose.material3.IconButton(
                    onClick = { launcher.launch("Salary_Statement_${profile.selectedStageName}.pdf") },
                    modifier = Modifier.size(36.dp).background(com.example.ui.theme.AccentPurple.copy(alpha=0.2f), RoundedCornerShape(8.dp))
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material.icons.Icons.Rounded.PictureAsPdf,
                        contentDescription = "Download PDF",
                        tint = com.example.ui.theme.AccentPurpleLight,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))'''

content = content.replace(header_target, header_replacement)

if 'import androidx.compose.ui.Alignment' not in content:
    content = content.replace('import androidx.compose.ui.Modifier', 'import androidx.compose.ui.Alignment\nimport androidx.compose.ui.Modifier')
if 'import androidx.compose.material.icons.Icons' not in content:
    content = content.replace('import androidx.compose.ui.Modifier', 'import androidx.compose.material.icons.Icons\nimport androidx.compose.material.icons.rounded.PictureAsPdf\nimport androidx.compose.ui.Modifier')
if 'import androidx.compose.foundation.background' not in content:
    content = content.replace('import androidx.compose.ui.Modifier', 'import androidx.compose.foundation.background\nimport androidx.compose.ui.Modifier')

with open('app/src/main/java/com/example/ui/components/salary/DetailedCalculationCard.kt', 'w') as f:
    f.write(content)
