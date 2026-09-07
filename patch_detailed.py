with open('app/src/main/java/com/example/ui/components/salary/DetailedCalculationCard.kt', 'r') as f:
    content = f.read()

# Fix the launcher code
old_launcher = '''                val launcher = androidx.compose.activity.compose.rememberLauncherForActivityResult(
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
                }'''

new_launcher = '''                val launcher = androidx.activity.compose.rememberLauncherForActivityResult(
                    androidx.activity.result.contract.ActivityResultContracts.CreateDocument("application/pdf")
                ) { uri ->
                    if (uri != null) {
                        try {
                            val pfd = context.contentResolver.openFileDescriptor(uri, "w")
                            if (pfd != null) {
                                java.io.FileOutputStream(pfd.fileDescriptor).use { outputStream ->
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
                                }
                                pfd.close()
                                android.widget.Toast.makeText(context, "PDF Saved", android.widget.Toast.LENGTH_SHORT).show()
                            }
                        } catch(e: Exception) {
                            e.printStackTrace()
                            android.widget.Toast.makeText(context, "Failed to save PDF", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    }
                }'''

content = content.replace(old_launcher, new_launcher)

with open('app/src/main/java/com/example/ui/components/salary/DetailedCalculationCard.kt', 'w') as f:
    f.write(content)
