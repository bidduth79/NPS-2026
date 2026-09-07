package com.example.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.askGemini
import com.example.data.AppDatabase
import com.example.data.ChatMessageEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val dao = db.chatDao()

    val chatHistory = dao.getAllMessages()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun sendMessage(text: String) {
        viewModelScope.launch {
            // Save user message
            dao.insertMessage(ChatMessageEntity(text = text, isUser = true))
            
            _isLoading.value = true
            
            // Get API Key from SharedPreferences
            val sharedPrefs = getApplication<Application>().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val customApiKey = sharedPrefs.getString("custom_api_key", null)
            
            val profileManager = com.example.data.UserProfileManager.getInstance(getApplication())
            val profile = profileManager.profile.value
            val response = askGemini(text, customApiKey, profile)
            
            // Save AI response
            dao.insertMessage(ChatMessageEntity(text = response, isUser = false))
            
            _isLoading.value = false
        }
    }
    
    fun clearHistory() {
        viewModelScope.launch {
            dao.clearHistory()
        }
    }
}
