with open('app/src/main/java/com/example/utils/SmsParser.kt', 'r') as f:
    content = f.read()

# Add EFT ignore logic right after extracting the msgLower variable
eft_logic = """
        // Ignore EFT messages as requested
        if (msgLower.contains("eft")) return null
        """

content = content.replace('val msgLower = message.lowercase()', 'val msgLower = message.lowercase()\n' + eft_logic)

with open('app/src/main/java/com/example/utils/SmsParser.kt', 'w') as f:
    f.write(content)
