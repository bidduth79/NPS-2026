sed -i '34i import kotlinx.coroutines.launch\nimport androidx.compose.ui.focus.onFocusChanged' app/src/main/java/com/example/ui/components/AiChatSheet.kt
sed -i 's/val keyboardController =/val coroutineScope = rememberCoroutineScope()\n    val keyboardController =/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
sed -i 's/modifier = Modifier.weight(1f),/modifier = Modifier.weight(1f).onFocusChanged { if (it.isFocused) coroutineScope.launch { sheetState.expand() } },/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
