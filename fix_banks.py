with open('app/src/main/java/com/example/utils/SmsParser.kt', 'r') as f:
    content = f.read()

new_banks = """            senderUpper.contains("MTB") -> "MTB"
            senderUpper.contains("SHIMANTO") || senderUpper.contains("SIMANTO") -> "Shimanto Bank"
            senderUpper.contains("SONALI") -> "Sonali Bank"
            senderUpper.contains("JANATA") -> "Janata Bank\""""

content = content.replace('            senderUpper.contains("MTB") -> "MTB"', new_banks)

with open('app/src/main/java/com/example/utils/SmsParser.kt', 'w') as f:
    f.write(content)
