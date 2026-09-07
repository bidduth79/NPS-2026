sed -i 's/val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)/val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
sed -i 's/\.fillMaxHeight(0.9f)/.fillMaxHeight()/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
sed -i 's/"AI অ্যাসিস্ট্যান্ট"/"AI Assistant"/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
sed -i 's/"বেতন স্কেল", "টিফিন ভাতা", "পেনশন", "উৎসব ভাতা"/"Pay Scale", "Tiffin Allowance", "Pension", "Festival Allowance"/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
sed -i 's/"হ্যালো! আমি PayScale AI. আপনার প্রশ্ন করতে পারেন।"/"Hello! I am AI Assistant. Ask me anything."/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
sed -i 's/"প্রশ্ন লিখুন..."/"Type a message..."/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
