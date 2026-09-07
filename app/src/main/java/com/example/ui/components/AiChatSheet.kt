package com.example.ui.components

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.ChipBg

import com.example.ui.theme.DarkDialogBg

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.chat.AiChatHeader
import com.example.ui.components.chat.AiChatQuickChips
import com.example.ui.components.chat.ChatBubble
import com.example.ui.components.chat.ChatInputArea
import com.example.ui.theme.BluePrimary
import com.example.viewmodel.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiChatSheet(
    onDismiss: () -> Unit,
    viewModel: ChatViewModel = viewModel()
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val messages by viewModel.chatHistory.collectAsStateWithLifecycle(initialValue = emptyList())
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    
    var inputText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val speechLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = data?.firstOrNull()
            if (!spokenText.isNullOrBlank()) {
                inputText = spokenText
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = DarkDialogBg,
        scrimColor = Color.Black.copy(alpha = 0.3f),
        contentColor = Color.White,
        contentWindowInsets = { WindowInsets.ime },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .imePadding()
                .fillMaxHeight(0.95f)
        ) {
            // Header
            AiChatHeader(onClearHistory = { viewModel.clearHistory() })
            
            // Quick Chips
            AiChatQuickChips(onSuggestionClick = { inputText = it })
            
            // Chat Messages
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                reverseLayout = true
            ) {
                if (messages.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            Text(stringResource(id = R.string.chat_greeting), color = Color.Gray)
                        }
                    }
                } else {
                    items(messages.reversed()) { message ->
                        ChatBubble(message)
                    }
                }
            }
            
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    color = BluePrimary,
                    trackColor = ChipBg
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Input Area
            ChatInputArea(
                inputText = inputText,
                onInputTextChanged = { inputText = it },
                isLoading = isLoading,
                onSend = {
                    viewModel.sendMessage(inputText)
                    inputText = ""
                    keyboardController?.hide()
                },
                coroutineScope = coroutineScope,
                sheetState = sheetState,
                keyboardController = keyboardController,
                speechLauncher = speechLauncher
            )
        }
    }
}
